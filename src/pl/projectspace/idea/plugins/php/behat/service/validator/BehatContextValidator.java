package pl.projectspace.idea.plugins.php.behat.service.validator;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import pl.projectspace.idea.plugins.commons.php.utils.PhpClassUtils;
import pl.projectspace.idea.plugins.php.behat.context.BehatContext;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatContextValidator {

    /**
     * Check if give PhpClass implementation is Behat Context implementation
     *
     * @param phpClass
     * @return
     */
    public boolean isBehatContext(PhpClass phpClass) {
        return (phpClass != null
                && isSubClassOfBehatContext(phpClass)
                && !PhpClassUtils.isInExcludedNamespace(phpClass, BehatContext.BUILDIN_CONTEXT_NAMESPACES));
    }

    /**
     * Checks if give class is subclass of BehatContext
     *
     * @param phpClass
     * @return
     */
    private boolean isSubClassOfBehatContext(PhpClass phpClass) {
        return PhpIndex.getInstance(phpClass.getProject()).getAllSubclasses(BehatContext.BASE_CONTEXT_INTERFACE)
                .contains(phpClass);
    }

    public boolean isValidCall(MethodReference methodReference) {
        return (methodReference != null && isValidMethodName(methodReference) && gotValidParameters(methodReference));
    }

    public boolean isValidMethodName(MethodReference reference) {
        return reference.getName().equalsIgnoreCase("getSubContext");
    }

    private boolean gotValidParameters(MethodReference methodReference) {
        PsiElement[] parameters = methodReference.getParameters();

        return (parameters.length == 1 && (parameters[0] instanceof StringLiteralExpression));
    }
}
