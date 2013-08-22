package pl.projectspace.idea.plugins.php.behat.core;

import com.intellij.openapi.actionSystem.AnActionEvent;
import pl.projectspace.idea.plugins.commons.php.action.DirectoryAction;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class BehatDirectoryAction extends DirectoryAction {

    @Override
    public void update(AnActionEvent e) {
        project = e.getProject();

        String relativePath = getRelativeDirectory();

        String requiredDir = getActionDirectory();

        if (!requiredDir.startsWith("/")) {
            requiredDir = "/" + requiredDir;
        }

        if (relativePath == null || !requiredDir.equals(relativePath)) {
            e.getPresentation().setVisible(false);
            e.getPresentation().setEnabled(false);
        }
    }

}
