package pl.projectspace.idea.plugins.php.behat.extensions.pageobject;

import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.php.behat.context.BehatContext;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectContext extends BehatContext {

    /**
     * Base PageObject Object PageObjectContext class
     */
    public final static String BASE_PAGE_OBJECT_CONTEXT_INTERFACE = "\\SensioLabs\\Behat\\PageObjectExtension\\Context\\PageObjectAwareInterface";

    public PageObjectContext(PhpClass phpClass) {
        super(phpClass);
    }

}
