package pl.projectspace.idea.plugins.php.behat.config.profile;

import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class Paths {

    public String features = "features";

    public String bootstrap = "features/bootstrap";

    public Paths(Map<String, String> paths) {
        if (paths.containsKey("features")) {
            this.features = paths.get("features");
        }

        if (paths.containsKey("bootstrap")) {
            this.bootstrap = paths.get("bootstrap");
        }
    }

    public String getFeaturesDir() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getBootstrap() {
        return bootstrap;
    }

    public void setBootstrap(String bootstrap) {
        this.bootstrap = bootstrap;
    }
}
