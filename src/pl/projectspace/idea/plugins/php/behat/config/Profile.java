package pl.projectspace.idea.plugins.php.behat.config;

import pl.projectspace.idea.plugins.php.behat.config.profile.Context;
import pl.projectspace.idea.plugins.php.behat.config.profile.Filters;
import pl.projectspace.idea.plugins.php.behat.config.profile.Formatter;
import pl.projectspace.idea.plugins.php.behat.config.profile.Paths;
import pl.projectspace.idea.plugins.php.behat.config.profile.extension.Extension;
import pl.projectspace.idea.plugins.php.behat.config.profile.extension.ExtensionInterface;
import pl.projectspace.idea.plugins.php.behat.config.profile.extension.MinkExtension;
import pl.projectspace.idea.plugins.php.behat.config.profile.extension.PageObjectExtension;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class Profile {

    private String name;
    private Context context;
    private Filters filters;
    private Paths paths;
    private Formatter formatter;
    private Map<String, ExtensionInterface> extensions = new HashMap<String, ExtensionInterface>();

    public Profile(String name, Map<String, Object> definition) {
        this.name = name;

        if (definition.containsKey("context")) {
            this.context = new Context((Map<String, Object>) definition.get("context"));
        }

        if (definition.containsKey("filters")) {
            this.filters = new Filters((Map<String, String>) definition.get("filters"));
        }

        if (definition.containsKey("paths")) {
            this.paths = new Paths((Map<String, String>) definition.get("paths"));
        }

        if (definition.containsKey("formatter")) {
            this.formatter = new Formatter((Map<String, Object>) definition.get("formatter"));
        }

        if (definition.containsKey("extensions")) {
            loadExtensions((Map<String, Map<String, Object>>) definition.get("extensions"));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

    public Paths getPaths() {
        return paths;
    }

    public void setPaths(Paths paths) {
        this.paths = paths;
    }

    public Formatter getFormatter() {
        return formatter;
    }

    public void setFormatter(Formatter formatter) {
        this.formatter = formatter;
    }

    public ExtensionInterface getExtension(String name) {
        return extensions.get(name);
    }

    private void loadExtensions(Map<String, Map<String, Object>> extensions) {
        for (Map.Entry<String, Map<String, Object>> entry: extensions.entrySet()) {
            ExtensionInterface extension = null;
            if (entry.getKey().equals("SensioLabs\\Behat\\PageObjectExtension\\Extension")) {
                extension = new PageObjectExtension(entry.getValue());
            } else if (entry.getKey().equals("Behat\\MinkExtension\\Extension")) {
                extension = new MinkExtension(entry.getValue());
            } else {
                extension = new Extension(entry.getKey(), entry.getValue());
            }

            this.extensions.put(extension.getName(), extension);
        }
    }

}
