package pl.projectspace.idea.plugins.php.behat.service.locator;

import com.intellij.openapi.project.Project;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.commons.php.service.locator.BasePhpClassLocator;
import pl.projectspace.idea.plugins.commons.php.service.locator.PhpClassLocatorInterface;
import pl.projectspace.idea.plugins.commons.php.utils.PhpClassUtils;
import pl.projectspace.idea.plugins.php.behat.behat.context.BehatContext;
import pl.projectspace.idea.plugins.php.behat.behat.context.ContextFactory;
import pl.projectspace.idea.plugins.php.behat.behat.context.PageObjectContext;

import java.util.*;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatContextLocator extends BasePhpClassLocator implements PhpClassLocatorInterface {

    /**
     * Base Interface for Behat Context for All Object
     */
    public static final String BASE_CONTEXT_INTERFACE = "\\Behat\\Behat\\Context\\ContextInterface";

    /**
     * Base context class
     */
    public static final String BASE_CONTEXT_CLASS = "\\Behat\\Behat\\Context\\BehatContext";

    /**
     * Base PageObject Object Context class
     */
    private final static String BASE_PAGE_OBJECT_CONTEXT_INTERFACE = "\\SensioLabs\\Behat\\PageObjectExtension\\Context\\PageObjectAwareInterface";

    /**
     * Class name of main context
     */
    public static final String MAIN_CONTEXT_CLASS = "\\FeatureContext";

    public static final LinkedList<String> BUILDIN_CONTEXT_NAMESPACES = new LinkedList<String>();

    /**
     * Base PageObject Object Context instance
     */
    public PageObjectContext basePageObjectContext;

    /**
     * Main Behat Context class instance
     */
    private BehatContext mainContext;

    static {
        BUILDIN_CONTEXT_NAMESPACES.add("\\Behat\\Behat\\Snippet\\");
    }

    public BehatContextLocator(Project project, PhpIndex index) {
        super(project, index);
        PhpClass mainContext = null;
        if ((mainContext =PsiTreeUtils.getClassByFQN(project, MAIN_CONTEXT_CLASS)) != null) {
            this.mainContext = new BehatContext(mainContext);
        }
    }

    @Override
    public List<Class> getSupportedTypes() {
        return new ArrayList<Class>(Arrays.asList(BehatContext.class));
    }

    @Override
    public PhpClass locate(String key) {
        return null;
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

        return mainContext;
    }

    /**
     * Check if give PhpClass implementation is Behat Context implementation
     *
     * @param phpClass
     * @return
     */
    public boolean isBehatContext(PhpClass phpClass) {
        return (phpClass != null
                && isSubClassOfBehatContext(phpClass)
                && !PhpClassUtils.isInExcludedNamespace(phpClass, BehatContextLocator.BUILDIN_CONTEXT_NAMESPACES));
    }

    /**
     * Check if give PhpClass instance is PageObject implementation
     *
     * @param phpClass
     * @return
     */
    public boolean isPageObjectContext(PhpClass phpClass) {
        return (isBehatContext(phpClass)
                && isSubClassOfPageObjectContext(phpClass));
    }

    /**
     * Checks if give class is subclass of BehatContext
     *
     * @param phpClass
     * @return
     */
    private boolean isSubClassOfBehatContext(PhpClass phpClass) {
        return PhpIndex.getInstance(phpClass.getProject()).getAllSubclasses(BASE_CONTEXT_INTERFACE)
                .contains(phpClass);
    }

    /**
     * Checks if given class is subclass of PageObject Context
     *
     * @param phpClass
     * @return
     */
    private boolean isSubClassOfPageObjectContext(PhpClass phpClass) {
        return PhpIndex.getInstance(phpClass.getProject()).getAllSubclasses(BASE_PAGE_OBJECT_CONTEXT_INTERFACE)
                .contains(phpClass);
    }

}
