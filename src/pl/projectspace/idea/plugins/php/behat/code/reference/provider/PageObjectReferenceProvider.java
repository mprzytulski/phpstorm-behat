package pl.projectspace.idea.plugins.php.behat.code.reference.provider;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.psi.element.context.PageObjectContext;
import pl.projectspace.idea.plugins.php.behat.psi.element.page.PageObject;
import pl.projectspace.idea.plugins.php.behat.psi.reference.PageObjectReference;
import pl.projectspace.idea.plugins.php.behat.service.locator.PageObjectLocator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement psiElement, @NotNull ProcessingContext processingContext) {
        MethodReference reference = PsiTreeUtil.getParentOfType(psiElement, MethodReference.class);

        if (!PageObjectContext.is(reference)) {
            return new PsiReference[0];
        }

        StringLiteralExpressionImpl name = ((StringLiteralExpressionImpl) psiElement);

        PageObject page = null;

        if((page = ServiceManager.getService(psiElement.getProject(), PageObjectLocator.class).get(name.getContents())) == null) {
            return new PsiReference[0];
        }

        PsiReference[] ref = { new PageObjectReference(page, name) };

        return ref;
    }

}
