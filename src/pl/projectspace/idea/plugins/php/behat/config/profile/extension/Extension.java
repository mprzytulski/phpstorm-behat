package pl.projectspace.idea.plugins.php.behat.config.profile.extension;

import pl.projectspace.idea.plugins.php.behat.config.Behat;

import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class Extension implements ExtensionInterface {

    protected final Behat behat;
    protected String name;

    protected Map<String, String> parameters;

    public Extension(Behat behat, String name, Map<String, Object> parameters) {
        this.behat = behat;
        this.name = name;
    }

    public Extension(Behat behat, String name) {
        this.behat = behat;
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<String, String> getParameters() {
        return parameters;
    }
}
