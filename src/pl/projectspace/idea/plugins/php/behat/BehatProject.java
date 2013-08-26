package pl.projectspace.idea.plugins.php.behat;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.php.PhpIndex;
import org.ho.yaml.Yaml;
import org.ho.yaml.exception.YamlException;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.ProjectComponent;
import pl.projectspace.idea.plugins.php.behat.config.Behat;
import pl.projectspace.idea.plugins.php.behat.config.Profile;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatProject extends ProjectComponent {

    private final Behat config;

    private static boolean enabled = false;

    public BehatProject(Project project, PhpIndex index) {
        super(project, index);



        enabled = false;
        config = new Behat(project);

        loadConfiguration();
    }

    public Behat getConfig() {
        return config;
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "BehatProject";
    }

    private void loadConfiguration() {
        try {
            VirtualFile file = project.getBaseDir().findFileByRelativePath("behat.yml");

            if (file == null) {
                return;
            }
            String filePath = file.getCanonicalPath();
            HashMap<String, Object> profiles = (HashMap<String, Object>) Yaml.load(new File(filePath));

            for (Map.Entry<String, Object> entry : profiles.entrySet()) {
                Profile profile = new Profile(config, entry.getKey(), (Map<String, Object>) entry.getValue());
                config.addProfile(entry.getKey(), profile);
            }

            enabled = true;
        } catch (YamlException e) {
            return;
        }
        catch (FileNotFoundException e) {
            return;
        }
    }

    public static boolean isEnabled() {
        return enabled;
    }
}
