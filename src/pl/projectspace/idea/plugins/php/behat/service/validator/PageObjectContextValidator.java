package pl.projectspace.idea.plugins.php.behat.service.validator;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.sun.javaws.exceptions.InvalidArgumentException;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.php.behat.context.BehatContext;
import pl.projectspace.idea.plugins.php.behat.context.PageObjectContext;
import pl.projectspace.idea.plugins.php.behat.service.exceptions.InvalidMethodArgumentsException;
import pl.projectspace.idea.plugins.php.behat.service.locator.BehatContextLocator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectContextValidator implements MethodCallValidator {

    private BehatContextValidator behatContextValidator;

    public PageObjectContextValidator(BehatContextValidator behatContextValidator) {
        this.behatContextValidator = behatContextValidator;
    }

    /**
     * Check if give PhpClass instance is PageObject implementation
     *
     * @param phpClass
     * @return
     */
    public boolean isPageObjectContext(PhpClass phpClass) {
        return (behatContextValidator.isBehatContext(phpClass)
                && isSubClassOfPageObjectContext(phpClass));
    }

    /**
     * Checks if given class is subclass of PageObject Context
     *
     * @param phpClass
     * @return
     */
    private boolean isSubClassOfPageObjectContext(PhpClass phpClass) {
        return PhpIndex.getInstance(phpClass.getProject()).getAllSubclasses(PageObjectContext.BASE_PAGE_OBJECT_CONTEXT_INTERFACE)
                .contains(phpClass);
    }

    public boolean isValidCall(MethodReference methodReference) throws InvalidMethodArgumentsException {
        if (methodReference == null) {
            return false;
        }
        if (!gotValidParameters(methodReference)) {
            throw new InvalidMethodArgumentsException("Invalid arguments for getPage call");
        }

        return true;
    }

    private boolean gotValidParameters(MethodReference methodReference) {
        PsiElement[] parameters = methodReference.getParameters();

        return (parameters.length == 1 && (parameters[0] instanceof StringLiteralExpression));
    }

    public boolean isValidMethodName(MethodReference reference) {
        return (reference != null && reference.getName().equalsIgnoreCase("getPage"));
    }

}
