package pl.projectspace.idea.plugins.php.behat.code.completion.contributor;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import pl.projectspace.idea.plugins.php.behat.code.completion.provider.yaml.FileLevelCompletionProvider;
import pl.projectspace.idea.plugins.php.behat.code.completion.provider.yaml.FilterProfileLevelCompletionProvider;
import pl.projectspace.idea.plugins.php.behat.code.completion.provider.yaml.ProfileLevelCompletionProvider;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ConfigurationCompletionContributor extends CompletionContributor {

    /**
     * Registry code completion providers
     */
    public ConfigurationCompletionContributor() {
        extend(
            CompletionType.BASIC,
            FileLevelCompletionProvider.getLocation(),
            new FileLevelCompletionProvider()
        );

        extend(
            CompletionType.BASIC,
            ProfileLevelCompletionProvider.getLocation(),
            new ProfileLevelCompletionProvider()
        );

        extend(
            CompletionType.BASIC,
            FilterProfileLevelCompletionProvider.getLocation(),
            new FilterProfileLevelCompletionProvider()
        );
    }

}
