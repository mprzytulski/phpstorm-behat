package pl.projectspace.idea.plugins.php.behat.code.reference.provider;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.service.locator.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.context.PageObjectContext;
import pl.projectspace.idea.plugins.php.behat.page.PageObject;
import pl.projectspace.idea.plugins.php.behat.psi.reference.PageObjectReference;
import pl.projectspace.idea.plugins.php.behat.service.locator.PageObjectLocator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement psiElement, @NotNull ProcessingContext processingContext) {
        try {
            MethodReference reference = PsiTreeUtil.getParentOfType(psiElement, MethodReference.class);

            if (!PageObjectContext.isGetPageCallOn(reference) || !PageObjectContext.is(reference)) {
                return new PsiReference[0];
            }

            StringLiteralExpressionImpl name = ((StringLiteralExpressionImpl) psiElement);

            PageObject page = null;

            page = psiElement.getProject().getComponent(BehatProject.class)
                .getService(PageObjectLocator.class).locate(name.getContents());

            PsiReference[] ref = { new PageObjectReference(page, name) };

            return ref;
        } catch (MissingElementException e) {
            return new PsiReference[0];
        }
    }

}
