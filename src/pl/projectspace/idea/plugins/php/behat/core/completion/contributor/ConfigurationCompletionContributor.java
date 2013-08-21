package pl.projectspace.idea.plugins.php.behat.core.completion.contributor;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import pl.projectspace.idea.plugins.php.behat.core.completion.provider.yaml.FileLevelCompletionProvider;
import pl.projectspace.idea.plugins.php.behat.core.completion.provider.yaml.FilterProfileLevelCompletionProvider;
import pl.projectspace.idea.plugins.php.behat.core.completion.provider.yaml.ProfileLevelCompletionProvider;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ConfigurationCompletionContributor extends CompletionContributor {

    /**
     * Registry core completion providers
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
