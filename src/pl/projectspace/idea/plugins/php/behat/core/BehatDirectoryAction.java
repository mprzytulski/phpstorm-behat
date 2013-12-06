package pl.projectspace.idea.plugins.php.behat.core;

import com.intellij.openapi.actionSystem.AnActionEvent;
import pl.projectspace.idea.plugins.commons.php.action.DirectoryAction;
import pl.projectspace.idea.plugins.php.behat.BehatProject;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class BehatDirectoryAction extends DirectoryAction {

    @Override
    public void update(AnActionEvent e) {
        project = e.getProject();

        String relativePath = null;
        String requiredDir = null;
        if (project.getComponent(BehatProject.class).isEnabled()) {
            relativePath = getRelativeDirectory();
            requiredDir = getActionDirectory();

            relativePath = relativePath.replaceAll("^/", "").replaceAll("/$", "");
            requiredDir = requiredDir.replaceAll("^/", "").replaceAll("/$", "");
        }

        if (relativePath == null || !relativePath.startsWith(requiredDir)) {
            e.getPresentation().setVisible(false);
            e.getPresentation().setEnabled(false);
        }
    }

}
