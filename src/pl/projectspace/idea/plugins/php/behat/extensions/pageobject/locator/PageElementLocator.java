package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.locator;

import com.jetbrains.php.PhpClassHierarchyUtils;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.apache.commons.lang3.text.WordUtils;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.config.profile.extension.PageObjectExtension;
import pl.projectspace.idea.plugins.php.behat.core.BehatLocator;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.element.PageElement;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageElementLocator extends BehatLocator {

    private final PageObjectExtension extension;
    private final PhpClass baseElement;

    public PageElementLocator(BehatProject behat, PhpIndex index) {
        super(behat, index);

        extension = (PageObjectExtension) behat.getConfig().getDefaultProfile().getExtension("PageObjects");
        String baseElementName = extension.getBaseElementName();
        baseElement = behat.getService(PsiTreeUtils.class).getClassByFQN(baseElementName);
    }

    @Override
    public <T> T locate(String name) throws MissingElementException {
        String normalised = normaliseName(name);

        Map<String, PageElement> pages = getAll();
        if (!pages.containsKey(normalised)) {
            throw new MissingElementException("Failed to locate Page Element named: " + name);
        }

        return (T) pages.get(normalised);
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

    private String normaliseName(String name) {
        return WordUtils.capitalize(name).replaceAll(" ", "");
    }

    public boolean is(PhpClass phpClass) {
        return PhpClassHierarchyUtils.isSuperClass(baseElement, phpClass, true);
    }

}
