package pl.projectspace.idea.plugins.php.behat.config.profile;

import pl.projectspace.idea.plugins.php.behat.config.Behat;

import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class Paths {

    private final Behat behat;
    protected String features = "features";

    protected String bootstrap = "features/bootstrap";

    public Paths(Behat behat, Map<String, String> paths) {
        this.behat = behat;
        if (paths.containsKey("features")) {
            this.features = paths.get("features");
        }

        if (paths.containsKey("bootstrap")) {
            this.bootstrap = paths.get("bootstrap");
        }

        if (!features.endsWith("/")) {
            features = features + "/";
        }
    }

    public String getFeaturesDir() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getBootstrapDir() {
        return bootstrap;
    }

    public void setBootstrap(String bootstrap) {
        this.bootstrap = bootstrap;
    }
}
