package pl.projectspace.idea.plugins.php.behat.core;

import com.jetbrains.php.PhpIndex;
import pl.projectspace.idea.plugins.commons.php.StateComponentInterface;
import pl.projectspace.idea.plugins.commons.php.code.locator.GenericObjectLocator;
import pl.projectspace.idea.plugins.commons.php.utils.annotation.DependsOnPlugin;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.core.annotations.DependsOnBehatExtension;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class BehatLocator extends GenericObjectLocator {

    protected BehatProject behat;

    public BehatLocator(BehatProject behat, PhpIndex index) {
        super(index);
        this.behat = behat;

        if (isEnabled()) {
            setup();
        }
    }

    protected boolean isEnabled() {
        return gotRequiredExtension() && requiredPluginIsEnabled();
    }

    private boolean requiredPluginIsEnabled() {
        DependsOnPlugin pluginAnnotation = this.getClass().getAnnotation(DependsOnPlugin.class);

        return ((pluginAnnotation != null) && ((StateComponentInterface) behat.getProject().getComponent(pluginAnnotation.value())).isEnabled())
            || pluginAnnotation == null;
    }

    private boolean gotRequiredExtension() {
        DependsOnBehatExtension extensionAnnotation = this.getClass().getAnnotation(DependsOnBehatExtension.class);

        return (((extensionAnnotation != null) && behat.getConfig().getDefaultProfile().hasExtension(extensionAnnotation.value()))
            || extensionAnnotation == null);
    }

    protected abstract void setup();
}
