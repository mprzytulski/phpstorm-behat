package pl.projectspace.idea.plugins.php.behat.code.completion.contributor;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import org.jetbrains.plugins.cucumber.psi.GherkinTokenTypes;
import pl.projectspace.idea.plugins.php.behat.code.completion.provider.FeatureAnnotationProvider;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class FeatureCompletionContributor extends CompletionContributor {

    /**
     * Registry code contribution annotations
     */
    public FeatureCompletionContributor() {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(GherkinTokenTypes.TAG),
            new FeatureAnnotationProvider()
        );
    }

}
