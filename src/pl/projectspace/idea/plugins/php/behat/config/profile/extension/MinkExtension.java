package pl.projectspace.idea.plugins.php.behat.config.profile.extension;

import pl.projectspace.idea.plugins.php.behat.config.Behat;

import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class MinkExtension extends Extension {
    public MinkExtension(Behat behat, Map<String, Object> config) {
        super(behat, "Mink");
    }
}
