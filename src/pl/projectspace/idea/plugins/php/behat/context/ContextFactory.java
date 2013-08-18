package pl.projectspace.idea.plugins.php.behat.context;

import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.service.locator.BehatContextLocator;
import pl.projectspace.idea.plugins.php.behat.service.validator.BehatContextValidator;
import pl.projectspace.idea.plugins.php.behat.service.validator.PageObjectContextValidator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ContextFactory {

    private static Map<String, BehatContext> map = new HashMap<String, BehatContext>();

    /**
     * Create Context decorator class for given Php Class
     *
     * @param phpClass
     * @return
     */
    public static BehatContext create(@NotNull PhpClass phpClass) {
        if (map.keySet().contains(phpClass.getFQN())) {
            return map.get(phpClass.getFQN());
        }

        BehatContextValidator behatContextValidator = phpClass.getProject()
            .getComponent(BehatProject.class).getService(BehatContextValidator.class);

        PageObjectContextValidator pageObjectContextValidator = phpClass.getProject().getComponent(BehatProject.class)
            .getService(PageObjectContextValidator.class);

        BehatContext context = null;

        if (behatContextValidator.isBehatContext(phpClass)) {
            context = new PageObjectContext(phpClass);
        } else if (pageObjectContextValidator.isPageObjectContext(phpClass)) {
            context = new BehatContext(phpClass);
        }

        if (context != null) {
            map.put(phpClass.getFQN(), context);
        }

        return context;
    }
}
