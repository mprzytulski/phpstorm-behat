package pl.projectspace.idea.plugins.php.behat.service.locator;

import com.intellij.openapi.project.Project;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.commons.php.service.locator.BasePhpClassLocator;
import pl.projectspace.idea.plugins.commons.php.service.locator.PhpClassLocatorInterface;
import pl.projectspace.idea.plugins.php.behat.psi.element.page.PageObject;

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
    public PhpClass locate(String key) {
        return null;
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
//            if (!isInExcludedNamespace(page, PageObjectLocator.DEFAULT_NAMESPACES)) {
//                result.put(page.getName(), new PageObject(page));
//            }
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
        return false;
//        return (phpClass != null && PhpClassHierarchyUtils.isSuperClass(basePage, phpClass, false));
    }

    /**
     * Check if give PhpClass instance is PageObject implementation
     *
     * @param methodReference
     * @return
     */
    public boolean is(MethodReference methodReference) {
        PhpClass phpClass = PsiTreeUtil.getParentOfType(methodReference, PhpClass.class);

        return is(phpClass);
    }

}
