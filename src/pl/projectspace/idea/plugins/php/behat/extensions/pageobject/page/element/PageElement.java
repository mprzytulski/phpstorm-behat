package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.element;

import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.commons.php.psi.element.PhpClassDecorator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageElement extends PhpClassDecorator {
    public PageElement(PhpClass phpClass) {
        super(phpClass);
    }
}
