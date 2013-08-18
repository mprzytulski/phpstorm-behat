package pl.projectspace.idea.plugins.php.behat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.ho.yaml.Yaml;
import pl.projectspace.idea.plugins.php.behat.config.Behat;
import pl.projectspace.idea.plugins.php.behat.config.Profile;
import pl.projectspace.idea.plugins.php.behat.config.converter.BehatConverter;
import pl.projectspace.idea.plugins.php.behat.config.converter.ProfileConverter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class Configuration {

    private Project project;

    private VirtualFile configPath;

    private Behat behat = new Behat();

    public Configuration(Project project) {
        this.project = project;
        configPath = project.getBaseDir().findFileByRelativePath("behat.yml");
        this.parseConfig(configPath);
    }

    public Behat getBehatConfiguration() {
        return behat;
    }

    private void parseConfig(VirtualFile file) {
        try {
            HashMap<String, Object> profiles = (HashMap<String, Object>) Yaml.load(new File(file.getCanonicalPath()));

            for (Map.Entry<String, Object> entry : profiles.entrySet()) {
                Profile profile = new Profile(entry.getKey(), (Map<String, Object>) entry.getValue());
                behat.addProfile(entry.getKey(), profile);
            }
        } catch (FileNotFoundException e) {
            return;
        }
    }

}
