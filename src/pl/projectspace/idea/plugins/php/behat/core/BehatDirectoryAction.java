package pl.projectspace.idea.plugins.php.behat.core;

import com.intellij.openapi.actionSystem.AnActionEvent;
import pl.projectspace.idea.plugins.commons.php.action.DirectoryAction;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.core.annotations.BehatAction;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class BehatDirectoryAction extends DirectoryAction {

    @Override
    @BehatAction
    public void update(AnActionEvent e) {
        project = e.getProject();

        String relativePath = null;
        String requiredDir = null;
        if (BehatProject.isEnabled()) {
            relativePath = getRelativeDirectory();
            requiredDir = getActionDirectory();

            relativePath = relativePath.replaceAll("^/", "").replaceAll("/$", "");
            requiredDir = requiredDir.replaceAll("^/", "").replaceAll("/$", "");
        }

        if (relativePath == null || !requiredDir.equals(relativePath)) {
            e.getPresentation().setVisible(false);
            e.getPresentation().setEnabled(false);
        }
    }

}
