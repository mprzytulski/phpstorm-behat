package pl.projectspace.idea.plugins.php.behat.code.completion.provider;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.cucumber.psi.GherkinFeature;
import pl.projectspace.idea.plugins.php.behat.code.annotation.BehatAnnotation;
import pl.projectspace.idea.plugins.php.behat.psi.element.lookup.SimpleTextLookup;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class FeatureAnnotationProvider extends CompletionProvider<CompletionParameters>
{
    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
        PsiElement position = parameters.getPosition();

        PsiElement coveringElement = PsiTreeUtil.getParentOfType(position, GherkinFeature.class);
        if (!(coveringElement instanceof GherkinFeature)) {
            return;
        }

        for (String tag : BehatAnnotation.tags) {
            result.addElement(new SimpleTextLookup(tag));
        }
    }
}
