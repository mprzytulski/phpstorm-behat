package pl.projectspace.idea.plugins.php.behat.service;

import com.intellij.openapi.project.Project;
import com.jetbrains.php.PhpIndex;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public interface ProjectRelatedServiceInterface {

    public void setProject(Project project);

    public void setIndex(PhpIndex index);

    public void configure();
}
