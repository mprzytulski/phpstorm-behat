package pl.projectspace.idea.plugins.php.behat.service.resolver;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.FailedToLocateContainingClassException;
import pl.projectspace.idea.plugins.commons.php.service.locator.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.context.exceptions.InvalidReferenceMethodCall;
import pl.projectspace.idea.plugins.php.behat.page.PageObject;
import pl.projectspace.idea.plugins.php.behat.psi.reference.PageObjectReference;
import pl.projectspace.idea.plugins.php.behat.service.exceptions.InvalidMethodArgumentsException;
import pl.projectspace.idea.plugins.php.behat.service.locator.PageObjectLocator;
import pl.projectspace.idea.plugins.php.behat.service.validator.PageObjectContextValidator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectResolver {
    public PageObjectReference resolve(PsiElement element) throws InvalidReferenceMethodCall, InvalidMethodArgumentsException {
        try {
            MethodReference methodReference = PsiTreeUtil.getParentOfType(element, MethodReference.class);

            BehatProject behatProject = element.getProject().getComponent(BehatProject.class);
            PageObjectContextValidator validator = behatProject.getService(PageObjectContextValidator.class);

            if (!validator.isValidCall(methodReference)) {
                throw new InvalidReferenceMethodCall("Tried to get Sub Context on invalid method reference - wrong method name.");
            }

            PhpClass phpClass = behatProject.getService(PsiTreeUtils.class).getClass(element);
            if (!validator.isPageObjectContext(phpClass)) {
                throw new InvalidReferenceMethodCall("Tried to get PageObject context on invalid method reference - no context object.");
            }

            StringLiteralExpressionImpl name = ((StringLiteralExpressionImpl) element);

            PageObjectLocator pageObjectLocator = behatProject.getService(PageObjectLocator.class);
            PageObject page = pageObjectLocator.locate(name.getContents());

            return new PageObjectReference(new PageObject(phpClass), name);
        } catch (FailedToLocateContainingClassException e) {
            throw new InvalidReferenceMethodCall(e.getMessage());
        } catch (MissingElementException e) {
            throw new InvalidReferenceMethodCall(e.getMessage());
        }
    }
}
