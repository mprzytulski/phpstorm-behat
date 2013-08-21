package pl.projectspace.idea.plugins.php.behat.context;

import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.core.BehatLocator;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.PageObjectContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ContextLocator extends BehatLocator {

    /**
     * Base PageObject Object PageObjectContext instance
     */
    public PageObjectContext basePageObjectContext;

    /**
     * Main Behat PageObjectContext class instance
     */
    private BehatContext mainContext;




    public ContextLocator(BehatProject behat, PhpIndex index) {
        super(behat, index);

        String mainContextClass = behat.getConfig().getDefaultProfile().getContext().getMainContextClass();

        PhpClass mainContext = behat.getService(PsiTreeUtils.class).getClassByFQN(mainContextClass);
        this.mainContext = new BehatContext(mainContext);
    }

    @Override
    public BehatContext locate(String key) throws MissingElementException {
        Map<String, BehatContext> all = getAll();
        if (!all.containsKey(key)) {
            throw new MissingElementException("Missing PageObjectContext");
        }

        return all.get(key);
    }

    /**
     * Get list of all available Behat Contexts in project scope
     *
     * @return
     */
    public Map<String, BehatContext> getAll() {
        Map<String, BehatContext> map = new HashMap<String, BehatContext>();

        for(PhpClass phpClass : index.getAllSubclasses(BehatContext.BASE_CONTEXT_INTERFACE)) {
            BehatContext context = new BehatContext(phpClass);
            map.put(phpClass.getFQN(), context);
        }

        return map;
    }

    /**
     * Return main context instance
     *
     * @return
     */
    public BehatContext getMainContext() {
        return mainContext;
    }
}
