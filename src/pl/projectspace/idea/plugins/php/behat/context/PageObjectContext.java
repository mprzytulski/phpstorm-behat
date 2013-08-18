package pl.projectspace.idea.plugins.php.behat.context;

import com.intellij.openapi.components.ServiceManager;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.php.behat.service.locator.BehatContextLocator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectContext extends BehatContext {

    /**
     * Base PageObject Object Context class
     */
    public final static String BASE_PAGE_OBJECT_CONTEXT_INTERFACE = "\\SensioLabs\\Behat\\PageObjectExtension\\Context\\PageObjectAwareInterface";

    public PageObjectContext(PhpClass phpClass) {
        super(phpClass);
    }

}
