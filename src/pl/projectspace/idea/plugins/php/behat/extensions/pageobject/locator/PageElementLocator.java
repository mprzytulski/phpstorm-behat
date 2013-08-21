package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.locator;

import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.config.profile.extension.PageObjectExtension;
import pl.projectspace.idea.plugins.php.behat.core.locator.BehatLocator;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.element.PageElement;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageElementLocator extends BehatLocator {

    private final PageObjectExtension extension;

    public PageElementLocator(BehatProject behat, PhpIndex index) {
        super(behat, index);

        extension = (PageObjectExtension) behat.getConfig().getDefaultProfile().getExtension("PageObjects");
    }

    @Override
    public <T> T locate(String name) throws MissingElementException {
        Map<String, PageElement> pages = getAll();
        if (!pages.containsKey(name)) {
            throw new MissingElementException("Failed to locate Page Element named: " + name);
        }

        return (T) pages.get(name);
    }

    public Map<String, PageElement> getAll() {
        Map<String, PageElement> result = new HashMap<String, PageElement>();

        for (PhpClass element : index.getAllSubclasses(extension.getBaseElementName())) {
            if (element.getFQN().startsWith(extension.getElementNamespace())) {
                result.put(element.getName(), new PageElement(element));
            }
        }

        return result;
    }

}
