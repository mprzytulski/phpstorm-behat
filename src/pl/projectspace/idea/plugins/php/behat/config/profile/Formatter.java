package pl.projectspace.idea.plugins.php.behat.config.profile;

import pl.projectspace.idea.plugins.php.behat.config.Behat;

import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class Formatter {

    private final Behat behat;
    public String name;

    public Map<String, String> parameters;

    public Formatter(Behat behat, Map<String, Object> formatter) {
        this.behat = behat;
        if (formatter.containsKey("name")) {
            this.name = (String)formatter.get("name");
        }

        if (formatter.containsKey("parameters")) {
            this.parameters = (Map<String, String>)formatter.get("parameters");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
