package pl.projectspace.idea.plugins.php.behat.psi.element;

import com.intellij.psi.PsiElement;
import pl.projectspace.idea.plugins.commons.php.psi.element.MethodDecorator;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;
import pl.projectspace.idea.plugins.php.behat.BehatProject;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class BehatMethod extends MethodDecorator {
    protected final BehatProject behatProject;

    public BehatMethod(PsiElement element) throws InvalidArgumentException {
        super(element);
        behatProject = element.getProject().getComponent(BehatProject.class);
    }
}
