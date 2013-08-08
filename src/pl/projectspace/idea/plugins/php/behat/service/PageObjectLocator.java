package pl.projectspace.idea.plugins.php.behat.service;

import com.intellij.openapi.project.Project;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;

import java.util.Collection;
import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectLocator {

    private final static String PAGE_OBJECT_CONTEXT_CLASS = "\\SensioLabs\\Behat\\PageObjectExtension\\Context\\PageObjectContext";

    private final static String PAGE_OBJECT_PAGE_CLASS = "\\SensioLabs\\Behat\\PageObjectExtension\\PageObject\\Page";

    public boolean isPageObjectContext(PhpClass phpClass) {
        for(PhpClass c : phpClass.getSupers()) {
            if (c.getFQN().equals(PAGE_OBJECT_CONTEXT_CLASS)) {
                return true;
            }
        }
        return false;
    }

    public PhpClass getPageObjectClass(Project project, String name) {
        Collection<PhpClass> pages = PhpIndex.getInstance(project).getAllSubclasses(PAGE_OBJECT_PAGE_CLASS);

        for (PhpClass page : pages) {
            if (page.getName().equals(name)) {
                return page;
            }
        }
        return null;
    }

    public Collection<PhpClass> getPages(Project project) {
        return PhpIndex.getInstance(project).getAllSubclasses(PAGE_OBJECT_PAGE_CLASS);
    }

}
