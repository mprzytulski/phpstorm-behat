package pl.projectspace.idea.plugins.php.behat.code.reference.provider;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.psi.referene.PageObjectReference;
import pl.projectspace.idea.plugins.php.behat.service.ContextLocator;

/**
 * @author Daniel Ancuta <whisller@gmail.com>
 */
public class SubcontextReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement psiElement, @NotNull ProcessingContext processingContext) {
        String name = ((StringLiteralExpressionImpl) psiElement).getContents();

        ContextLocator locator = ServiceManager.getService(psiElement.getProject(), ContextLocator.class);

        PhpClass subcontext = null;

        PhpClass mainContext = locator.getMainContextFor(PsiTreeUtil.getParentOfType(psiElement, PhpClass.class));

        if (mainContext != null) {
            subcontext = locator.getSubContextFor(
                locator.getMainContextFor(PsiTreeUtil.getParentOfType(psiElement, PhpClass.class)),
                name
            );
        }

        PsiReference[] ref = { new PageObjectReference(subcontext, (StringLiteralExpressionImpl) psiElement) };

        return ref;
    }

}
