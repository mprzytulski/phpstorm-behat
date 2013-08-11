package pl.projectspace.idea.plugins.php.behat.psi.element;

import com.jetbrains.php.lang.psi.elements.PhpClass;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
abstract public class PhpClassDecorator {

    protected PhpClass phpClass;

    /**
     * Create PhpClass decorator
     *
     * @param phpClass
     */
    public PhpClassDecorator(PhpClass phpClass) {
        this.phpClass = phpClass;
    }

    /**
     * Get decorated PhpClass instance
     *
     * @return
     */
    public PhpClass getDecoratedObject() {
        return this.phpClass;
    }
}
