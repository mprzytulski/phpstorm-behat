package pl.projectspace.idea.plugins.php.behat.psi.reference;

import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import pl.projectspace.idea.plugins.php.behat.psi.element.context.BehatContext;
import pl.projectspace.idea.plugins.php.behat.psi.element.context.page.PageObject;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatContextReference extends PhpClassReference {

    private BehatContext behatContext;

    /**
     * @param behatContext
     * @param element
     */
    public BehatContextReference(BehatContext behatContext, final StringLiteralExpression element) {
        super(behatContext.getDecoratedObject(), element);
        this.behatContext = behatContext;
    }

    public BehatContext getBehatContext() {
        return behatContext;
    }

}
