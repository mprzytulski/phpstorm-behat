package pl.projectspace.idea.plugins.php.behat.config.profile;

import pl.projectspace.idea.plugins.php.behat.config.Behat;

import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class Filters {

    private final Behat behat;
    private String tags;

    private String name;

    public Filters(Behat behat, Map<String, String> filters) {
        this.behat = behat;
        if (filters.containsKey("tags")) {
            this.tags = filters.get("tags");
        }

        if (filters.containsKey("name")) {
            this.tags = filters.get("name");
        }
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
