package pl.projectspace.idea.plugins.php.behat.psi.reference;

import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.psi.reference.PhpClassReference;
import pl.projectspace.idea.plugins.php.behat.page.PageObject;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectReference extends PhpClassReference {

    private PageObject pageObject;

    /**
     * @param pageObject
     * @param element
     */
    public PageObjectReference(@NotNull PageObject pageObject, final StringLiteralExpression element) {
        super(pageObject.getDecoratedObject(), element);
        this.pageObject = pageObject;
    }

    public PageObject getPageObject() {
        return pageObject;
    }

}
