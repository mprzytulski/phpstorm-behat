package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.element;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;
import pl.projectspace.idea.plugins.commons.php.psi.reference.PhpClassReference;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ElementReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext processingContext) {
        MethodReference methodReference = PsiTreeUtil.getParentOfType(element, MethodReference.class);

        try {
            GetElementMethodDecorator method = new GetElementMethodDecorator(methodReference);

            if (!method.isResolvableToType()) {
                return new PsiReference[0];
            }

            return new PsiReference[] { new PhpClassReference(method.getReturnType().getDecoratedObject(), method.getParameter(0)) };
        } catch (InvalidArgumentException e) {
            return new PsiReference[0];
        } catch (MissingElementException e) {
            return new PsiReference[0];
        }
    }

}
