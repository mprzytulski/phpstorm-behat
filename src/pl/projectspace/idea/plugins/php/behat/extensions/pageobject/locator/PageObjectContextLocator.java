package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.locator;

import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.core.locator.BehatLocator;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.PageObjectContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectContextLocator extends BehatLocator {

    public PageObjectContextLocator(BehatProject behat, PhpIndex index) {
        super(behat, index);
    }

    /**
     * Get list of all available Behat Contexts in project scope
     *
     * @return
     */
    public Map<String, PageObjectContext> getAll() {
        Map<String, PageObjectContext> map = new HashMap<String, PageObjectContext>();

        for(PhpClass phpClass : index.getAllSubclasses(PageObjectContext.BASE_PAGE_OBJECT_CONTEXT_INTERFACE)) {
            map.put(phpClass.getFQN(), new PageObjectContext(phpClass));
        }

        return map;
    }

    @Override
    public PageObjectContext locate(String key) throws MissingElementException {
        Map<String, PageObjectContext> all = getAll();
        if (!all.containsKey(key)) {
            throw new MissingElementException("Missing PageObjectContext");
        }

        return all.get(key);
    }

}
