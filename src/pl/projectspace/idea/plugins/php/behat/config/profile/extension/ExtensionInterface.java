package pl.projectspace.idea.plugins.php.behat.config.profile.extension;

import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
interface ExtensionInterface {

    public String getName();

    public Map<String, String> getParameters();

}
