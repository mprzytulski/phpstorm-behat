package pl.projectspace.idea.plugins.php.behat;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.jetbrains.php.PhpIndex;
import org.ho.yaml.Yaml;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.config.Behat;
import pl.projectspace.idea.plugins.php.behat.config.Profile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatProject implements ProjectComponent {

    private final Behat config;

    protected Project project;
    private PhpIndex index;

    public BehatProject(Project project, PhpIndex index) {
        this.project = project;
        this.index = index;

        config = new Behat(project);

        loadConfiguration();
    }

    public PhpIndex getIndex() {
        return index;
    }

    public <T>T getService(@NotNull Class<T> service) {
        return (T) ServiceManager.getService(project, service);
    }

    public Behat getConfig() {
        return config;
    }

    @Override
    public void projectOpened() {
    }

    @Override
    public void projectClosed() {
    }

    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "BehatProject";
    }

    private void loadConfiguration() {
        try {
            String file = project.getBaseDir().findFileByRelativePath("behat.yml").getCanonicalPath();
            HashMap<String, Object> profiles = (HashMap<String, Object>) Yaml.load(new File(file));

            for (Map.Entry<String, Object> entry : profiles.entrySet()) {
                Profile profile = new Profile(config, entry.getKey(), (Map<String, Object>) entry.getValue());
                config.addProfile(entry.getKey(), profile);
            }
        } catch (FileNotFoundException e) {
            return;
        }
    }
}
