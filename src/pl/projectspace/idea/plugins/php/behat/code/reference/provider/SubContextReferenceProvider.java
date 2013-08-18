package pl.projectspace.idea.plugins.php.behat.code.reference.provider;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.FailedToLocateContainingClassException;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.context.exceptions.InvalidReferenceMethodCall;
import pl.projectspace.idea.plugins.php.behat.service.resolver.SubContextResolver;
import pl.projectspace.idea.plugins.php.behat.service.validator.BehatContextValidator;

/**
 * @author Daniel Ancuta <whisller@gmail.com>
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class SubContextReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext processingContext) {
        MethodReference methodReference = PsiTreeUtil.getParentOfType(element, MethodReference.class);

        try {
            BehatProject behatProject = element.getProject().getComponent(BehatProject.class);
            BehatContextValidator validator = behatProject.getService(BehatContextValidator.class);

            if (!validator.isValidCall(methodReference)) {
                return new PsiReference[0];
            }

            PhpClass phpClass = behatProject.getService(PsiTreeUtils.class).getClass(element);
            if (!validator.isBehatContext(phpClass)) {
                throw new InvalidReferenceMethodCall("Tried to get Sub Context on invalid method reference - no context object.");
            }

            SubContextResolver service = methodReference.getProject().getComponent(BehatProject.class).getService(SubContextResolver.class);

            return new PsiReference[] { service.resolve(methodReference) };
        } catch (InvalidReferenceMethodCall invalidReferenceMethodCall) {
            return new PsiReference[0];
        } catch (FailedToLocateContainingClassException e) {
            return new PsiReference[0];
        }
    }

}
