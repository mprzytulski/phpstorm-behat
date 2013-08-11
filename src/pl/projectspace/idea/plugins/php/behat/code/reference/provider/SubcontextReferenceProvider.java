package pl.projectspace.idea.plugins.php.behat.code.reference.provider;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.psi.element.context.BehatContext;
import pl.projectspace.idea.plugins.php.behat.psi.reference.BehatContextReference;
import pl.projectspace.idea.plugins.php.behat.psi.utils.PsiUtils;
import pl.projectspace.idea.plugins.php.behat.service.locator.ContextLocator;

/**
 * @author Daniel Ancuta <whisller@gmail.com>
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class SubContextReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement psiElement, @NotNull ProcessingContext processingContext) {
        String contextLocation = ServiceManager.getService(psiElement.getProject(), ContextLocator.class).getBaseDir().getCanonicalPath();

        MethodReference methodReference = PsiTreeUtil.getParentOfType(psiElement, MethodReference.class);
        PhpClass phpClass = PsiUtils.getClass(methodReference, contextLocation);
        if (methodReference == null || !methodReference.getName().equalsIgnoreCase("getSubContext") || !BehatContext.is(phpClass)) {
            return new PsiReference[0];
        }

        StringLiteralExpressionImpl name = ((StringLiteralExpressionImpl) psiElement);

        BehatContext context = new BehatContext(phpClass);

        BehatContext subContext = null;
        if ((subContext = context.getSubContext(name.getContents())) == null) {
            return new PsiReference[0];
        }

        PsiReference[] ref = { new BehatContextReference(subContext, name) };

        return ref;
    }

}
