package pl.projectspace.idea.plugins.php.behat.service.locator;

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.LocalSearchScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.Query;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.commons.php.service.locator.BasePhpClassLocator;
import pl.projectspace.idea.plugins.commons.php.service.locator.PhpClassLocatorInterface;
import pl.projectspace.idea.plugins.commons.php.utils.PhpClassUtils;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.config.profile.extension.PageObjectExtension;
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

        BehatProject behatProject = project.getComponent(BehatProject.class);

        PageObjectExtension extension = (PageObjectExtension) behatProject.getConfig().getDefaultProfile().getExtension("PageObjects");

        String basePageName = extension.getBasePageName();

        basePage = behatProject.getService(PsiTreeUtils.class).getClassByFQN(basePageName);
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
        if (basePage == null) {
            return loadFromFiles();
        } else {
            return loadFromIndex();
        }
    }

    private Map<String, PageObject> loadFromIndex() {
        Map<String, PageObject> result = new HashMap<String, PageObject>();

        for (PhpClass page : index.getAllSubclasses(basePage.getFQN())) {
            if (!PhpClassUtils.isInExcludedNamespace(page, PageObjectLocator.DEFAULT_NAMESPACES)) {
                result.put(page.getName(), new PageObject(page));
            }
        }

        return result;
    }

    private Map<String, PageObject> loadFromFiles() {
        Map<String, PageObject> result = new HashMap<String, PageObject>();

        return result;
    }

}
