package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.locator;


import com.jetbrains.php.PhpIndex;
import pl.projectspace.idea.plugins.commons.php.code.locator.GenericObjectLocator;

public abstract class BehatExtensionElementLocator extends GenericObjectLocator {

    protected BehatExtensionElementLocator(PhpIndex index) {
        super(index);
    }

}
