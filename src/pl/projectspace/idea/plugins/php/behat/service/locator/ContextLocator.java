package pl.projectspace.idea.plugins.php.behat.service.locator;

import com.jetbrains.php.PhpClassHierarchyUtils;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.php.behat.psi.element.context.BehatContext;
import pl.projectspace.idea.plugins.php.behat.psi.element.context.ContextFactory;
import pl.projectspace.idea.plugins.php.behat.psi.element.context.PageObjectContext;
import pl.projectspace.idea.plugins.php.behat.psi.utils.PsiUtils;
import pl.projectspace.idea.plugins.php.behat.service.ProjectRelatedService;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ContextLocator extends ProjectRelatedService {

    /**
     * Base Interface for Behat Context for All Object
     */
    public static final String BASE_CONTEXT_INTERFACE = "\\Behat\\Behat\\Context\\ExtendedContextInterface";

    /**
     * Base context class
     */
    public static final String BASE_CONTEXT_CLASS = "\\Behat\\Behat\\Context\\BehatContext";

    /**
     * Base PageObject Object Context class
     */
    private final static String PAGE_OBJECT_CONTEXT_CLASS = "\\SensioLabs\\Behat\\PageObjectExtension\\Context\\PageObjectContext";

    /**
     * Class name of main context
     */
    public static final String MAIN_CONTEXT_CLASS = "\\FeatureContext";

    /**
     * Base Context
     */
    private BehatContext baseContext;

    /**
     * Base PageObject Object Context instance
     */
    public PageObjectContext basePageObjectContext;

    /**
     * Main Behat Context class instance
     */
    private BehatContext mainContext;

    /**
     * Configure current service
     */
    @Override
    public void configure() {
        PhpClass baseContext = null;

        if ((baseContext = PsiUtils.getClassByFQN(project, BASE_CONTEXT_CLASS)) != null) {
            this.baseContext = new BehatContext(baseContext);
        }

        PhpClass mainContext = null;
        if ((mainContext = PsiUtils.getClassByFQN(project, MAIN_CONTEXT_CLASS)) != null) {
            this.mainContext = new BehatContext(mainContext);
        }

        PhpClass pageObjectContext = null;
        if ((pageObjectContext = PsiUtils.getClassByFQN(project, PAGE_OBJECT_CONTEXT_CLASS)) != null) {
            basePageObjectContext = new PageObjectContext(pageObjectContext);
        }

        baseDir = project.getBaseDir().findFileByRelativePath("features/bootstrap");
    }

    /**
     * Get list of all available Behat Contexts in project scope
     *
     * @return
     */
    public Collection<BehatContext> getAll() {
        ArrayList<BehatContext> list = new ArrayList<BehatContext>();

        for(PhpClass phpClass : index.getAllSubclasses(BASE_CONTEXT_CLASS)) {
            BehatContext context = null;
            if ((context = ContextFactory.create(phpClass)) != null) {
                list.add(context);
            }
        }

        return list;
    }

    /**
     * Return main context instance
     *
     * @return
     */
    public BehatContext getMainContext() {
        ensure(initiated);

        return mainContext;
    }

    /**
     * Return instance of base Behat Context class
     *
     * @return
     */
    public BehatContext getBaseContext() {
        ensure(initiated);
        return baseContext;
    }

    /**
     * Check if given PhpClass implementation is Behat Context implementation
     *
     * @param phpClass
     * @return boolean
     */
    public boolean isBehatContext(PhpClass phpClass) {
        ensure(initiated);
        return (phpClass != null && PhpClassHierarchyUtils.isSuperClass(baseContext.getDecoratedObject(), phpClass, false) && isInProjectScope(phpClass));
    }

    /**
     * Check if give PhpClass instance is PageObject implementation
     *
     * @param phpClass
     * @return boolean
     */
    public boolean isPageObjectContext(PhpClass phpClass) {
        ensure(initiated);
        return (phpClass != null && PhpClassHierarchyUtils.isSuperClass(basePageObjectContext.getDecoratedObject(), phpClass, false));
    }
}
