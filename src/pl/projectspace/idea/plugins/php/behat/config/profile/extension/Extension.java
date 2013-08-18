package pl.projectspace.idea.plugins.php.behat.config.profile.extension;

import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class Extension implements ExtensionInterface {

    protected String name;

    protected Map<String, String> parameters;

    public Extension(String name, Map<String, Object> parameters) {
        this.name = name;
    }

    public Extension(String name) {
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
