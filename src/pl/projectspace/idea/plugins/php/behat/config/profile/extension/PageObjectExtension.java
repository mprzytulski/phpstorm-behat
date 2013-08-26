package pl.projectspace.idea.plugins.php.behat.config.profile.extension;

import com.intellij.openapi.vfs.VirtualFile;
import pl.projectspace.idea.plugins.php.behat.config.Behat;

import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectExtension extends Extension {

    private String pageNamespace = "\\";

    private String elementNamespace = "\\";

    public PageObjectExtension(Behat behat, Map<String, Object> config) {
        super(behat, "PageObjects");

        if (config.containsKey("namespaces")) {
            Map<String, Object> names = (Map<String, Object>) config.get("namespaces");
            if (names.containsKey("page")) {
                this.pageNamespace = (String) names.get("page");
            }

            if (names.containsKey("element")) {
                this.elementNamespace = (String) names.get("element");
            }

            if (elementNamespace.equals("\\") && !pageNamespace.equals("\\")) {
                elementNamespace = pageNamespace.concat("\\Element");
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

    public String getBasePageName() {
        return "\\SensioLabs\\Behat\\PageObjectExtension\\PageObject\\Page";
    }

    public String getBaseElementName() {
        return "\\SensioLabs\\Behat\\PageObjectExtension\\PageObject\\Element";
    }

    public VirtualFile getPageDirectory() {
        String path = behat.getDefaultProfile().getPaths().getFeaturesDir().concat(pageNamespace.replaceAll("\\\\", "/")).concat("/");
        VirtualFile dir = behat.getProject().getBaseDir().findFileByRelativePath(path);
        if (dir == null) {
            dir = behat.getProject().getBaseDir().findFileByRelativePath(
                behat.getDefaultProfile().getPaths().getBootstrapDir().concat("Page")
            );
        }

        return dir;
    }

    public VirtualFile getPageElementDirectory() {
        String path = behat.getDefaultProfile().getPaths().getFeaturesDir().concat(elementNamespace.replaceAll("\\\\", "/")).concat("/");
        VirtualFile dir = behat.getProject().getBaseDir().findFileByRelativePath(path);
        if (dir == null) {
            dir = behat.getProject().getBaseDir().findFileByRelativePath(
                behat.getDefaultProfile().getPaths().getBootstrapDir().concat("Page/Element")
            );
        }

        return dir;
    }
}
