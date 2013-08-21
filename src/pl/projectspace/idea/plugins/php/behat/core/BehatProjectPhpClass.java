package pl.projectspace.idea.plugins.php.behat.core;

import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.commons.php.psi.element.PhpClassDecorator;
import pl.projectspace.idea.plugins.php.behat.BehatProject;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
abstract public class BehatProjectPhpClass extends PhpClassDecorator {

    /**
     * Create PhpClass decorator
     *
     * @param phpClass
     */
    public BehatProjectPhpClass(PhpClass phpClass) {
        super(phpClass);
    }

    /**
     * Return BehatProject for this class
     *
     * @return
     */
    public BehatProject getBehatProject() {
        if (element != null) {
            return (BehatProject)element.getProject().getComponent("BehatProject");
        }

        return null;
    }

}
