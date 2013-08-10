package pl.projectspace.idea.plugins.php.behat.code.reference.provider;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.psi.PhpClassReference;
import pl.projectspace.idea.plugins.php.behat.psi.referene.PageObjectReference;
import pl.projectspace.idea.plugins.php.behat.service.PageObjectLocator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement psiElement, @NotNull ProcessingContext processingContext) {

        String name = ((StringLiteralExpressionImpl) psiElement).getContents();

        PhpClass page = ServiceManager.getService(psiElement.getProject(), PageObjectLocator.class).getPage(name);
        PsiReference[] ref = { new PageObjectReference(page, (StringLiteralExpressionImpl) psiElement) };

        return ref;
    }

}
