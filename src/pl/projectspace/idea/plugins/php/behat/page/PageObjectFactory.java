package pl.projectspace.idea.plugins.php.behat.page;

import com.jetbrains.php.lang.psi.elements.PhpClass;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectFactory {

    public PageObject createFor(PhpClass phpClass) {
        return new PageObject(phpClass);
    }
}
