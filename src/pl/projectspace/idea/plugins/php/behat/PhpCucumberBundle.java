package pl.projectspace.idea.plugins.php.behat;

import com.intellij.CommonBundle;
import com.intellij.reference.SoftReference;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.PropertyKey;

import java.lang.ref.Reference;
import java.util.ResourceBundle;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PhpCucumberBundle {

    @NonNls
    public static final String BUNDLE = "pl.projectspace.idea.plugins.php.behat.PhpCucumberBundle";
    private static Reference<ResourceBundle> ourBundle;

    public static String message(@NonNls @PropertyKey(resourceBundle="pl.projectspace.idea.plugins.php.behat.PhpCucumberBundle") String key, Object[] params)
    {
        return CommonBundle.message(getBundle(), key, params);
    }

    private static ResourceBundle getBundle() {
        ResourceBundle bundle = null;
        if (ourBundle != null) bundle = (ResourceBundle)ourBundle.get();
        if (bundle == null) {
            bundle = ResourceBundle.getBundle("pl.projectspace.idea.plugins.php.behat.PhpCucumberBundle");
            ourBundle = new SoftReference(bundle);
        }
        return bundle;
    }
}