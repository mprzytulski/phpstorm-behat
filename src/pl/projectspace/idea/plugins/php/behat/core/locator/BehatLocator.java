package pl.projectspace.idea.plugins.php.behat.core.locator;

import com.jetbrains.php.PhpIndex;
import pl.projectspace.idea.plugins.commons.php.code.locator.GenericObjectLocator;
import pl.projectspace.idea.plugins.php.behat.BehatProject;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class BehatLocator extends GenericObjectLocator {

    protected BehatProject behat;

    protected BehatLocator(BehatProject behat, PhpIndex index) {
        super(index);
        this.behat = behat;
    }
}
