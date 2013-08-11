package pl.projectspace.idea.plugins.php.behat.psi.element.context;

import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;

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

        BehatContext context = null;
        if (PageObjectContext.is(phpClass)) {
            context = new PageObjectContext(phpClass);
        } else if (BehatContext.is(phpClass)) {
            context = new BehatContext(phpClass);
        }

        if (context != null) {
            map.put(phpClass.getFQN(), context);
        }

        return context;
    }
}
