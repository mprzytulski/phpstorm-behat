package pl.projectspace.idea.plugins.php.behat.code.reference.provider;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.php.behat.behat.context.BehatContext;
import pl.projectspace.idea.plugins.php.behat.psi.reference.BehatContextReference;

/**
 * @author Daniel Ancuta <whisller@gmail.com>
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class SubContextReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement psiElement, @NotNull ProcessingContext processingContext) {
        MethodReference methodReference = PsiTreeUtil.getParentOfType(psiElement, MethodReference.class);

        PhpClass phpClass = PsiTreeUtils.getClass(methodReference);

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
