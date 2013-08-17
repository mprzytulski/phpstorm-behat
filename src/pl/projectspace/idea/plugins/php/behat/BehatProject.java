package pl.projectspace.idea.plugins.php.behat;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.jetbrains.php.PhpIndex;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.service.Locator;
import pl.projectspace.idea.plugins.commons.php.service.locator.PhpClassLocatorInterface;
import pl.projectspace.idea.plugins.php.behat.behat.Configuration;
import pl.projectspace.idea.plugins.php.behat.behat.page.PageObjectFactory;
import pl.projectspace.idea.plugins.php.behat.service.locator.*;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatProject implements ProjectComponent {

    private final Configuration configuration;

    protected Project project;
    private PhpIndex index;

    private Locator locator;
    private final PageObjectFactory pageObjectFactory;

    public BehatProject(Project project, PhpIndex index, Locator locator, PageObjectFactory pageObjectFactory) {
        this.project = project;
        this.index = index;
        this.locator = locator;
        this.pageObjectFactory = pageObjectFactory;

        locator.registryLocator(new BehatContextLocator(project, index));
        locator.registryLocator(new PageObjectLocator(project, index));
//        locator.registryLocator(new StepLocator(project, index));

        configuration = new Configuration(project);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Locator getLocator() {
        return locator;
    }

    public PhpIndex getIndex() {
        return index;
    }

    public PageObjectFactory getPageObjectFactory() {
        return pageObjectFactory;
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
