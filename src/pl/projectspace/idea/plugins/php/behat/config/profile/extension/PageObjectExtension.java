package pl.projectspace.idea.plugins.php.behat.config.profile.extension;

import java.util.List;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectExtension extends Extension {

    private String pageNamespace = "\\";

    private String elementNamespace = "\\";

    public PageObjectExtension(Map<String, Object> config) {
        super("PageObjects");

        if (config.containsKey("namespaces")) {
            Map<String, Object> names = (Map<String, Object>) config.get("namespaces");
            if (names.containsKey("page")) {
                this.pageNamespace = (String) names.get("page");
            }

            if (names.containsKey("element")) {
                this.elementNamespace = (String) names.get("element");
            }
        }
    }

    public String getPageNamespace() {
        return pageNamespace;
    }

    public void setPageNamespace(String pageNamespace) {
        this.pageNamespace = pageNamespace;
    }

    public String getElementNamespace() {
        return elementNamespace;
    }

    public void setElementNamespace(String elementNamespace) {
        this.elementNamespace = elementNamespace;
    }
}
