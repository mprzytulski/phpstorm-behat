package pl.projectspace.idea.plugins.php.behat.context.action;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiDirectory;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class DirectoryAction extends AnAction {

    protected String getRelativeDirectory(AnActionEvent e) {
        return getSelectedDirectory(e).getVirtualFile().getPath().replace(e.getProject().getBasePath(), "");
    }

    protected PsiDirectory getSelectedDirectory(AnActionEvent e) {
        ProjectView view = ProjectView.getInstance(e.getProject());
        PsiDirectory[] selected = view.getCurrentProjectViewPane().getSelectedDirectories();

        if (selected.length == 0) {
            return null;
        }

        return selected[0];
    }

}
