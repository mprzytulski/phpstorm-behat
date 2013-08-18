package pl.projectspace.idea.plugins.php.behat.config;

import pl.projectspace.idea.plugins.php.behat.config.exception.ConfigurationException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class Behat extends pl.projectspace.idea.plugins.commons.php.code.yml.File {

    private Map<String, Profile> profiles = new HashMap<String, Profile>();

    public Behat() {

    }

    public Map<String, Class> getAllowedElements() {
        return Collections.emptyMap();
    }

    public Map<String, Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(Map<String, Profile> profiles) {
        this.profiles = profiles;
    }

    public void addProfile(String name, Profile profile) {
        this.profiles.put(name, profile);
    }

    public Profile getProfile(String name) {
        return profiles.get(name);
    }

    public Profile getDefaultProfile() {
        return getProfile("default");
    }

}
