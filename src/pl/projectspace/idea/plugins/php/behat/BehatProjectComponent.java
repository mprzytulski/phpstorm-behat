package pl.projectspace.idea.plugins.php.behat;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.jetbrains.php.PhpIndex;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.service.ContextLocator;
import pl.projectspace.idea.plugins.php.behat.service.PageObjectLocator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatProjectComponent implements ProjectComponent {

    private Project project;

    public BehatProjectComponent(Project project) {
        this.project = project;
    }

    @Override
    public void projectOpened() {
        PhpIndex index = PhpIndex.getInstance(project);

        PageObjectLocator pageObjectLocator = ServiceManager.getService(project, PageObjectLocator.class);
        pageObjectLocator.setIndex(index);
        pageObjectLocator.setProject(project);

        ContextLocator contextLocator = ServiceManager.getService(project, ContextLocator.class);
        contextLocator.setIndex(index);
        contextLocator.setProject(project);
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
        return "Behat";
    }
}
