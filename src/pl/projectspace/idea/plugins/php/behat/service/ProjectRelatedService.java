package pl.projectspace.idea.plugins.php.behat.service;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
abstract public class ProjectRelatedService implements ProjectRelatedServiceInterface {

    /**
     * Base PageObject instance
     */
    public PhpClass basePage;
    protected Project project;

    protected PhpIndex index;

    protected boolean initiated = false;

    /**
     * Base dir for project PageObject
     */
    public VirtualFile baseDir;

    @Override
    public void setProject(Project project) {
        this.project = project;
    }

    public void setIndex(PhpIndex index) {
        this.index = index;
    }

    public void ensure(boolean initiated) {
        if (initiated) {
            return;
        }

        configure();

        this.initiated = true;
    }

    /**
     * Check if given Page belongs to project test scope
     *
     * @param phpClass
     * @return
     */
    public boolean isInProjectScope(PhpClass phpClass) {
        ensure(initiated);
        return phpClass.getContainingFile().getOriginalFile().getContainingDirectory().getVirtualFile().getCanonicalPath().startsWith(
            getBaseDir().getCanonicalPath()
        );
    }

    public VirtualFile getBaseDir() {
        ensure(initiated);
        return baseDir;
    }
}
