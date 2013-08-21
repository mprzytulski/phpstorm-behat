package pl.projectspace.idea.plugins.php.behat.config.profile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class Context {

    private String klass = "\\FeatureContext";

    private Map<String, String> parameters = new HashMap<String, String>();

    public Context(Map<String, Object> context) {
        if (context.containsKey("class")) {
            this.klass = (String)context.get("class");
        }

        if (context.containsKey("parameters")) {
            this.parameters = (Map<String, String>)context.get("parameters");
        }
    }

    public String getMainContextClass() {
        return klass;
    }

    public void setMainContextClass(String klass) {
        this.klass = klass;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
}
