package pl.projectspace.idea.plugins.php.behat.psi.element.context.page;

import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.php.behat.psi.element.PhpClassDecorator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObject extends PhpClassDecorator {

    /**
     * Create PhpClass decorator
     *
     * @param phpClass
     */
    public PageObject(PhpClass phpClass) {
        super(phpClass);
    }
}
