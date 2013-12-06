package pl.projectspace.idea.plugins.php.behat.context;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.utils.annotation.DependsOnPlugin;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
@DependsOnPlugin("behat")
public class ContextAnnotationProvider extends CompletionProvider<CompletionParameters>
{
    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
        PsiElement position = parameters.getPosition();
        PsiElement parent = position.getParent();

//        PhpClass phpClass = PsiTreeUtil.getParentOfType(position, PhpClass.class);
//        if (phpClass == null || !BehatContext.is(phpClass)) {
//            return;
//        }
//
//        for (Object annotation : ArrayUtils.addAll(BehatAnnotation.tags, BehatAnnotation.hook)) {
//            result.addElement(new SimpleTextLookup("@" + (String)annotation));
//        }
    }
}
