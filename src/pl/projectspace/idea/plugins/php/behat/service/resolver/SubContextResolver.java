package pl.projectspace.idea.plugins.php.behat.service.resolver;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.FailedToLocateContainingClassException;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.context.BehatContext;
import pl.projectspace.idea.plugins.php.behat.context.exceptions.InvalidReferenceMethodCall;
import pl.projectspace.idea.plugins.php.behat.context.exceptions.MissingContextException;
import pl.projectspace.idea.plugins.php.behat.psi.reference.BehatContextReference;
import pl.projectspace.idea.plugins.php.behat.service.validator.BehatContextValidator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class SubContextResolver {
    public BehatContextReference resolve(PsiElement element) throws InvalidReferenceMethodCall {
        try {
            MethodReference methodReference = PsiTreeUtil.getParentOfType(element, MethodReference.class);

            BehatProject behatProject = element.getProject().getComponent(BehatProject.class);
            BehatContextValidator validator = behatProject.getService(BehatContextValidator.class);

            if (!validator.isValidCall(methodReference)) {
                throw new InvalidReferenceMethodCall("Tried to get Sub Context on invalid method reference - wrong method name.");
            }

            PhpClass phpClass = behatProject.getService(PsiTreeUtils.class).getClass(element);
            if (!validator.isBehatContext(phpClass)) {
                throw new InvalidReferenceMethodCall("Tried to get Sub Context on invalid method reference - no context object.");
            }

            StringLiteralExpressionImpl name = ((StringLiteralExpressionImpl) element);

            BehatContext context = new BehatContext(phpClass);

            return new BehatContextReference(context.getSubContext(name.getContents()), name);
        } catch (FailedToLocateContainingClassException e) {
            throw new InvalidReferenceMethodCall(e.getMessage());
        } catch (MissingContextException e) {
            throw new InvalidReferenceMethodCall(e.getMessage());
        }
    }
}
