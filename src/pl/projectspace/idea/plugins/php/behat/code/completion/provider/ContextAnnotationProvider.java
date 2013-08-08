package pl.projectspace.idea.plugins.php.behat.code.completion.provider;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocExpectedExceptionTag;
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocTag;
import com.jetbrains.php.lang.psi.PhpPsiUtil;
import com.jetbrains.php.lang.psi.elements.*;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.code.annotation.BehatAnnotation;
import pl.projectspace.idea.plugins.php.behat.service.ContextLocator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ContextAnnotationProvider extends CompletionProvider<CompletionParameters>
{
    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
        PsiElement position = parameters.getPosition().getOriginalElement();
        PsiElement parent = position.getParent();

        PhpDocComment docComment = (PhpDocComment) PhpPsiUtil.getParentByCondition(position, PhpDocComment.INSTANCEOF);
        PhpDocTag docTag = (PhpDocTag)PhpPsiUtil.getParentByCondition(position, PhpDocTag.INSTANCEOF);

        boolean isExpectedExceptionTag = docTag instanceof PhpDocExpectedExceptionTag;

        if (docComment == null) return;

        PhpPsiElement next = docComment.getNextPsiSibling();

        if ((next instanceof GroupStatement)) {
            next = next.getFirstPsiChild();
        }

        PhpClass phpClass = (PhpClass) PsiTreeUtil.getParentOfType(position, new Class[]{PhpClass.class});
        if (phpClass == null) {
            return;
        }

        if (!ServiceManager.getService(phpClass.getProject(), ContextLocator.class).isContextClass(phpClass)) {
            return;
        }

        if (((parent instanceof PhpDocComment)) || ((parent instanceof PhpDocTag))) {
            boolean at = parent instanceof PhpDocTag;
            if (at) result.stopHere();

            else if ((next instanceof Method)) {
                for (String ann : BehatAnnotation.step) {
                    result.addElement(createDocTagLookup(at, "@" + ann));
                }

                for (String ann : BehatAnnotation.hook) {
                    result.addElement(createDocTagLookup(at, "@" + ann));
                }
            }
        }
    }

    private LookupElementBuilder createDocTagLookup(boolean at, String s)
    {
        return LookupElementBuilder.create(at ? s.substring(1) : s).withBoldness(true);
    }
}
