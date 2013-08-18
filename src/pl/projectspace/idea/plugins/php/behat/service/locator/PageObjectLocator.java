package pl.projectspace.idea.plugins.php.behat.service.locator;

import com.intellij.openapi.project.Project;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.commons.php.service.locator.BasePhpClassLocator;
import pl.projectspace.idea.plugins.commons.php.service.locator.PhpClassLocatorInterface;
import pl.projectspace.idea.plugins.commons.php.utils.PhpClassUtils;
import pl.projectspace.idea.plugins.php.behat.page.PageObject;
import pl.projectspace.idea.plugins.commons.php.service.locator.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.service.exceptions.MissingPageObjectException;

import java.util.*;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectLocator extends BasePhpClassLocator implements PhpClassLocatorInterface {

    /**
     * Base PageObject Object Class
     */
    private final static String PAGE_OBJECT_PAGE_CLASS = "\\SensioLabs\\Behat\\PageObjectExtension\\PageObject\\Page";

    public static final List<String> DEFAULT_NAMESPACES = new LinkedList<String>();

    private PhpClass basePage;

    public PageObjectLocator(Project project, PhpIndex index) {
        super(project, index);

        Collection<PhpClass> result = index.getClassesByFQN(PAGE_OBJECT_PAGE_CLASS);
        if (!result.isEmpty()) {
            basePage = result.iterator().next();
        }
    }

    @Override
    public List<Class> getSupportedTypes() {
        return new ArrayList<Class>(Arrays.asList(PageObject.class));
    }

    @Override
    public PageObject locate(String key) throws MissingElementException {
        Map<String, PageObject> pages = getAll();

        if (!pages.keySet().contains(key)) {
            throw new MissingPageObjectException("Missing PageObject class named: "+key);
        }

        return pages.get(key);
    }

    /**
     * Return map of all available Pages
     *
     * @return
     */
    public Map<String, PageObject> getAll() {
        Map<String, PageObject> result = new HashMap<String, PageObject>();

        for (PhpClass page : index.getAllSubclasses(PAGE_OBJECT_PAGE_CLASS)) {
            if (!PhpClassUtils.isInExcludedNamespace(page, PageObjectLocator.DEFAULT_NAMESPACES)) {
                result.put(page.getName(), new PageObject(page));
            }
        }

        return result;
    }

}
