package pl.projectspace.idea.plugins.php.behat.feature;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import org.jetbrains.plugins.cucumber.psi.GherkinTokenTypes;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class FeatureCompletionContributor extends CompletionContributor {

    /**
     * Registry core contribution annotations
     */
    public FeatureCompletionContributor() {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(GherkinTokenTypes.TAG),
            new FeatureAnnotationProvider()
        );
    }

}
