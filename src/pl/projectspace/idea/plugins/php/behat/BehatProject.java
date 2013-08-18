package pl.projectspace.idea.plugins.php.behat;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.jetbrains.php.PhpIndex;
import org.jetbrains.annotations.NotNull;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatProject implements ProjectComponent {

    private final Configuration configuration;

    protected Project project;
    private PhpIndex index;

    public BehatProject(Project project, PhpIndex index) {
        this.project = project;
        this.index = index;

        configuration = new Configuration(project);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public PhpIndex getIndex() {
        return index;
    }

    public <T>T getService(@NotNull Class<T> service) {
        return (T) ServiceManager.getService(project, service);
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
}
