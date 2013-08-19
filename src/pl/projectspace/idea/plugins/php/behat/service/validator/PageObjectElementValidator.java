package pl.projectspace.idea.plugins.php.behat.service.validator;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import pl.projectspace.idea.plugins.php.behat.service.exceptions.InvalidMethodArgumentsException;

import java.util.*;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectElementValidator extends BaseCallValidator {
    @Override
    protected List<Class> getSupportedElementsTypes() {
        return new ArrayList<Class>(Arrays.asList(MethodReference.class));
    }

    @Override
    protected Map<Class, String> getExpectedNames() {
        return new HashMap<Class, String>() {{
            put(MethodReference.class, "getElement");
        }};
    }

    @Override
    protected Class[] getExpectedParameters() {
        return new Class[] { StringLiteralExpression.class };
    }
}
