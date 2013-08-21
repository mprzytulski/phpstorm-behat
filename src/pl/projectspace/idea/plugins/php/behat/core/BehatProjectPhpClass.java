package pl.projectspace.idea.plugins.php.behat.core;

import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.commons.php.psi.element.PhpClassDecorator;
import pl.projectspace.idea.plugins.php.behat.BehatProject;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
abstract public class BehatProjectPhpClass extends PhpClassDecorator {

    protected final BehatProject behat;

    /**
     * Create PhpClass decorator
     *
     * @param phpClass
     */
    public BehatProjectPhpClass(PhpClass phpClass) {
        super(phpClass);

        behat = element.getProject().getComponent(BehatProject.class);
    }

    /**
     * Return BehatProject for this class
     *
     * @return
     */
    public BehatProject getBehatProject() {
        return behat;
    }

}
