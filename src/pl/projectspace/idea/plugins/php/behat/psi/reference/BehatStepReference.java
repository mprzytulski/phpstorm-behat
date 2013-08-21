package pl.projectspace.idea.plugins.php.behat.psi.reference;

import com.intellij.psi.PsiElement;
import pl.projectspace.idea.plugins.commons.php.psi.reference.PhpClassReference;
import pl.projectspace.idea.plugins.php.behat.feature.step.BehatStep;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatStepReference extends PhpClassReference {

    private BehatStep behatStep;

    /**
     * @param behatStep
     * @param element
     */
    public BehatStepReference(BehatStep behatStep, final PsiElement element) {
        super(behatStep.getMethod(), element);
        this.behatStep = behatStep;
    }

    public BehatStep getBehatStep() {
        return behatStep;
    }

}
