package pl.projectspace.idea.plugins.php.behat.service.validator;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import pl.projectspace.idea.plugins.php.behat.service.exceptions.InvalidMethodArgumentsException;

import java.util.List;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
abstract public class BaseCallValidator implements ValidatorInterface {

    private final List<Class> supportedElementstTypes;
    private final Map<Class, String> expectedNames;
    private final Class[] expectedParameters;

    protected BaseCallValidator() {
        supportedElementstTypes = getSupportedElementsTypes();
        expectedNames = getExpectedNames();
        expectedParameters = getExpectedParameters();
    }

    @Override
    public boolean isValidCall(PsiElement element) throws InvalidMethodArgumentsException {
        return (
            (element != null)
            && (supportedElementstTypes.contains(element.getClass()))
            && (isExpectedName(element))
            && (hasProperParameters(element))
        );
    }

    protected boolean hasProperParameters(PsiElement element) {
        if (!(element instanceof MethodReference)) {
            return true;
        }

        PsiElement[] parameters = ((MethodReference) element).getParameters();

        if (parameters.length < expectedParameters.length) {
            return false;
        }

        for (int i = 0; i < expectedParameters.length; i++) {
            if (expectedParameters[i] == null) {
                continue;
            }

            if (!parameters[i].getClass().isInstance(expectedParameters[i])) {
                return false;
            }
        }

        return true;
    }

    protected boolean isExpectedMethodName(MethodReference reference) {
        return expectedNames.get(reference.getClass()).equalsIgnoreCase(reference.getName());
    }

    protected boolean isExpectedName(PsiElement element) {
        if (!expectedNames.containsKey(element.getClass())) {
            return false;
        }

        if ((element instanceof MethodReference)) {
            return isExpectedMethodName((MethodReference) element);
        }

        return false;
    }

    protected Class[] getExpectedParameters() {
        return new Class[0];
    }

    abstract protected List<Class> getSupportedElementsTypes();
    abstract protected Map<Class, String> getExpectedNames();
}
