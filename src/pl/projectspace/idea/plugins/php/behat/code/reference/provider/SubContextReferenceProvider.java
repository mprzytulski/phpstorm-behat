package pl.projectspace.idea.plugins.php.behat.code.reference.provider;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.service.resolver.SubContextResolver;
import pl.projectspace.idea.plugins.php.behat.context.exceptions.InvalidReferenceMethodCall;

/**
 * @author Daniel Ancuta <whisller@gmail.com>
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class SubContextReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement psiElement, @NotNull ProcessingContext processingContext) {
        MethodReference methodReference = PsiTreeUtil.getParentOfType(psiElement, MethodReference.class);

        try {
            SubContextResolver service = methodReference.getProject().getComponent(BehatProject.class).getService(SubContextResolver.class);

            return new PsiReference[] { service.resolve(methodReference) };
        } catch (InvalidReferenceMethodCall invalidReferenceMethodCall) {
            return new PsiReference[0];
        }
    }

}
