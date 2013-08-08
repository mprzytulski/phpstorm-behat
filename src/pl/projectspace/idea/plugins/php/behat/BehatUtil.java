package pl.projectspace.idea.plugins.php.behat;

import com.jetbrains.php.lang.psi.elements.Method;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatUtil {

    public static boolean isStepDefinition(Method method) {
        return true;
    }

}
