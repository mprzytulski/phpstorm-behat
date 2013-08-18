package pl.projectspace.idea.plugins.php.behat.code.type;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.resolve.types.PhpTypeProvider2;
import org.jetbrains.annotations.Nullable;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.context.exceptions.InvalidReferenceMethodCall;
import pl.projectspace.idea.plugins.php.behat.psi.reference.BehatContextReference;
import pl.projectspace.idea.plugins.php.behat.service.resolver.SubContextResolver;
import pl.projectspace.idea.plugins.php.behat.service.validator.BehatContextValidator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ContextTypeProvider extends AbstractClassTypeProvider implements PhpTypeProvider2 {

    @Nullable
    @Override
    public String getType(PsiElement psiElement) {
        try {
            MethodReference methodReference = PsiTreeUtil.getParentOfType(psiElement, MethodReference.class);

            BehatProject behatProject = psiElement.getProject().getComponent(BehatProject.class);

            BehatContextValidator validator = behatProject.getService(BehatContextValidator.class);
            SubContextResolver service = behatProject.getService(SubContextResolver.class);

            if (!validator.isValidCall(methodReference)) {
                return null;
            }

            PhpClass phpClass = behatProject.getService(PsiTreeUtils.class).getClass(methodReference);
            if (!validator.isBehatContext(phpClass)) {
                throw new InvalidReferenceMethodCall("Tried to get Sub Context on invalid method reference - no context object.");
            }

            BehatContextReference reference = service.resolve(methodReference);

            return reference.getContext().getDecoratedObject().getFQN();
        } catch (InvalidReferenceMethodCall invalidReferenceMethodCall) {
            return null;
        }
    }

}
