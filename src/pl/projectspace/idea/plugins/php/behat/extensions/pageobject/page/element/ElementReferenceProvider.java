package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.element;

import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.ArrayHashElement;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;
import pl.projectspace.idea.plugins.commons.php.psi.reference.ArrayElementReference;
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

            PsiReference ref = null;
            if (method.getReturnType().getDecoratedObject() instanceof PhpClass) {
                ref = new PhpClassReference(method.getReturnType().getDecoratedObject(), method.getParameter(0));
            } else if (method.getReturnType().getDecoratedObject() instanceof ArrayHashElement) {
                ref = new ArrayElementReference(method.getReturnType().getDecoratedObject(), method.getParameter(0));
            }

            return new PsiReference[] { ref };
        } catch (InvalidArgumentException e) {
        } catch (MissingElementException e) {
        }

        return new PsiReference[0];
    }

}
