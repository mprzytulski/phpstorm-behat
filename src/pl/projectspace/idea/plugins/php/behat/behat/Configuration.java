package pl.projectspace.idea.plugins.php.behat.behat;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class Configuration {

    private Project project;

    public Configuration(Project project) {
        this.project = project;
    }

    public VirtualFile getFeaturesDirectory() {
        return project.getBaseDir().findFileByRelativePath("features");
    }

    public VirtualFile getBehatContextBaseDirectory() {
        return project.getBaseDir().findFileByRelativePath("features/bootstrap");
    }

    public VirtualFile getPageObjectBaseDirectory() {
        return project.getBaseDir().findFileByRelativePath("features/bootstrap/Page");
    }

}
