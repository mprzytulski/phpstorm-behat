package pl.projectspace.idea.plugins.php.behat.psi.reference;

import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import pl.projectspace.idea.plugins.commons.php.psi.reference.PhpClassReference;
import pl.projectspace.idea.plugins.php.behat.context.BehatContext;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatContextReference extends PhpClassReference {

    private BehatContext context;

    /**
     * @param context
     * @param element
     */
    public BehatContextReference(BehatContext context, final StringLiteralExpression element) {
        super(context.getDecoratedObject(), element);
        this.context = context;
    }

    public BehatContext getContext() {
        return context;
    }

}
