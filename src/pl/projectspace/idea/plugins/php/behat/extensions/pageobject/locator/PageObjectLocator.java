package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.locator;

import com.jetbrains.php.PhpClassHierarchyUtils;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.commons.php.code.locator.GenericObjectLocator;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.commons.php.utils.PhpClassUtils;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.config.profile.extension.PageObjectExtension;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.PageObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectLocator extends GenericObjectLocator {

    private BehatProject behat;

    private static final List<String> DEFAULT_NAMESPACES = new LinkedList<String>() {{
        add("\\SensioLabs\\Behat\\PageObjectExtension\\PageObject");
    }};

    PhpClass basePage;

    public PageObjectLocator(BehatProject behat, PhpIndex index) {
        super(index);
        this.behat = behat;

        PageObjectExtension extension = (PageObjectExtension) behat.getConfig().getDefaultProfile().getExtension("PageObjects");
        String basePageName = extension.getBasePageName();
        basePage = behat.getService(PsiTreeUtils.class).getClassByFQN(basePageName);
    }

    @Override
    public <T> T locate(String name) throws MissingElementException {
        Map<String, PageObject> pages = getAll();
        if (!pages.containsKey(name)) {
            throw new MissingElementException("Failed to locate Page named: " + name);
        }

        return (T) pages.get(name);
    }

    public Map<String, PageObject> getAll() {
        Map<String, PageObject> result = new HashMap<String, PageObject>();

        for (PhpClass page : index.getAllSubclasses(basePage.getFQN())) {
            if (!PhpClassUtils.isInExcludedNamespace(page, PageObjectLocator.DEFAULT_NAMESPACES)) {
                result.put(page.getName(), new PageObject(page));
            }
        }

        return result;
    }

    public boolean is(PhpClass phpClass) {
        return PhpClassHierarchyUtils.isSuperClass(basePage, phpClass, true);
    }

}
