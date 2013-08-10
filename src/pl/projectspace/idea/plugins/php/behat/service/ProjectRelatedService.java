package pl.projectspace.idea.plugins.php.behat.service;

import com.intellij.openapi.project.Project;
import com.jetbrains.php.PhpIndex;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
abstract public class ProjectRelatedService implements ProjectRelatedServiceInterface {

    protected Project project;

    protected PhpIndex index;

    @Override
    public void setProject(Project project) {
        this.project = project;
    }

    public void setIndex(PhpIndex index) {
        this.index = index;
    }
}
