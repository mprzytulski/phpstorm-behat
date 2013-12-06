package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.locator;

import com.jetbrains.php.PhpClassHierarchyUtils;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.apache.commons.lang3.text.WordUtils;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.commons.php.utils.PhpStringUtils;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.config.profile.extension.PageObjectExtension;
import pl.projectspace.idea.plugins.php.behat.core.BehatLocator;
import pl.projectspace.idea.plugins.php.behat.core.annotations.DependsOnBehatExtension;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.element.PageElement;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
@DependsOnBehatExtension("PageObjects")
public class PageElementLocator extends BehatLocator {

    private PageObjectExtension extension;
    private PhpClass baseElement;

    protected PageElementLocator(BehatProject behat, PhpIndex index) {
        super(behat, index);
    }

    @Override
    protected void setup() {
        if (behat.getConfig().getDefaultProfile().hasExtension("PageObjects")) {
            extension = (PageObjectExtension) behat.getConfig().getDefaultProfile().getExtension("PageObjects");

            String baseElementName = (extension != null) ? extension.getBaseElementName() : "";
            baseElement = behat.getService(PsiTreeUtils.class).getClassByFQN(baseElementName);
        }
    }

    @Override
    public <T> T locate(String name) throws MissingElementException {
        String normalised = PhpStringUtils.normaliseToClassName(name);

        Map<String, PageElement> elements = getAll();
        if (!elements.containsKey(normalised)) {
            throw new MissingElementException("Failed to locate Page Element named: " + name);
        }

        return (T) elements.get(normalised);
    }

    public Map<String, PageElement> getAll() {
        Map<String, PageElement> result = new HashMap<String, PageElement>();
        getAllFromExtension(result);

        return result;
    }

    public boolean is(PhpClass phpClass) {
        return (baseElement != null) && PhpClassHierarchyUtils.isSuperClass(baseElement, phpClass, true);
    }

    protected void getAllFromExtension(Map<String, PageElement> result) {
        if (extension == null) {
            return;
        }

        for (PhpClass element : index.getAllSubclasses(extension.getBaseElementName())) {
            if (PhpStringUtils.belongsToNamespace(element.getFQN(), extension.getElementNamespace())) {
                result.put(element.getName(), new PageElement(element));
            }
        }
    }

}
