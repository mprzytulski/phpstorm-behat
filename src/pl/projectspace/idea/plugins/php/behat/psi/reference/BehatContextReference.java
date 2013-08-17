package pl.projectspace.idea.plugins.php.behat.psi.reference;

import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import pl.projectspace.idea.plugins.commons.php.psi.reference.PhpClassReference;
import pl.projectspace.idea.plugins.php.behat.context.BehatContext;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatContextReference extends PhpClassReference {

    private BehatContext behatStep;

    /**
     * @param behatStep
     * @param element
     */
    public BehatContextReference(BehatContext behatStep, final StringLiteralExpression element) {
        super(behatStep.getDecoratedObject(), element);
        this.behatStep = behatStep;
    }

    public BehatContext getBehatStep() {
        return behatStep;
    }

}
