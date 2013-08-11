package pl.projectspace.idea.plugins.php.behat.service.locator;

import com.jetbrains.php.PhpClassHierarchyUtils;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.php.behat.psi.element.page.PageObject;
import pl.projectspace.idea.plugins.php.behat.service.ProjectRelatedService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectLocator extends ProjectRelatedService {

    /**
     * Base PageObject Object Class
     */
    private final static String PAGE_OBJECT_PAGE_CLASS = "\\SensioLabs\\Behat\\PageObjectExtension\\PageObject\\Page";



    /**
     * Configure locator - create instances of base page object classes
     */
    public void configure() {
        Collection<PhpClass> result;

        result = index.getClassesByFQN(PAGE_OBJECT_PAGE_CLASS);
        if (!result.isEmpty()) {
            basePage = result.iterator().next();
        }

        baseDir = project.getBaseDir().findFileByRelativePath("features/bootstrap/Page");
    }

    /**
     * Get PageObject by given name
     *
     * @param name
     * @return
     */
    public PageObject get(String name) {
        Map<String, PageObject> pages = getAll();

        if (pages.keySet().contains(name)) {
            return pages.get(name);
        }

        return null;
    }

    /**
     * Return map of all available Pages
     *
     * @return
     */
    public Map<String, PageObject> getAll() {
        Map<String, PageObject> result = new HashMap<String, PageObject>();

        for (PhpClass page : index.getAllSubclasses(PAGE_OBJECT_PAGE_CLASS)) {
            if (isInProjectScope(page)) {
                result.put(page.getName(), new PageObject(page));
            }
        }

        return result;
    }

    /**
     * Check if give PhpClass instance is PageObject implementation
     *
     * @param phpClass
     * @return
     */
    public boolean is(PhpClass phpClass) {
        ensure(initiated);
        return (phpClass != null && PhpClassHierarchyUtils.isSuperClass(basePage, phpClass, false));
    }
}
