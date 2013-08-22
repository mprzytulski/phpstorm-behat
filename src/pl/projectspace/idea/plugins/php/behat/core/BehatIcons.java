package pl.projectspace.idea.plugins.php.behat.core;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatIcons {

    public static final Icon Icon = load("/pl/projectspace/idea/plugins/php/behat/icons/behat_16_16.png");

    private static Icon load(String path)
    {
        return IconLoader.getIcon(path, BehatIcons.class);
    }

}
