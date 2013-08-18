package pl.projectspace.idea.plugins.php.behat.service.locator;

import com.intellij.openapi.project.Project;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.commons.php.service.locator.BasePhpClassLocator;
import pl.projectspace.idea.plugins.commons.php.service.locator.PhpClassLocatorInterface;
import pl.projectspace.idea.plugins.commons.php.utils.PhpClassUtils;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.context.BehatContext;
import pl.projectspace.idea.plugins.php.behat.context.ContextFactory;
import pl.projectspace.idea.plugins.php.behat.context.PageObjectContext;

import java.util.*;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatContextLocator extends BasePhpClassLocator implements PhpClassLocatorInterface {

    /**
     * Base PageObject Object Context instance
     */
    public PageObjectContext basePageObjectContext;

    /**
     * Main Behat Context class instance
     */
    private BehatContext mainContext;


    public BehatContextLocator(Project project, PhpIndex index) {
        super(project, index);

        PhpClass mainContext = project.getComponent(BehatProject.class).getService(PsiTreeUtils.class).getClassByFQN(BehatContext.MAIN_CONTEXT_CLASS);
        this.mainContext = new BehatContext(mainContext);
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

        for(PhpClass phpClass : index.getAllSubclasses(BehatContext.BASE_CONTEXT_CLASS)) {
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
}
