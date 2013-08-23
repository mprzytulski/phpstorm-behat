package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDirectory;
import com.jetbrains.php.lang.PhpFileType;
import pl.projectspace.idea.plugins.commons.php.action.DirectoryAction;
import pl.projectspace.idea.plugins.commons.php.utils.FileFactory;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.config.profile.extension.PageObjectExtension;
import pl.projectspace.idea.plugins.php.behat.context.action.dialog.CreateFeatureDialog;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.action.dialog.CreatePageObjectDialog;

import java.util.Properties;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class CreatePageObjectFile extends DirectoryAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
    }

    @Override
    protected DialogWrapper getDialog() {
        return new CreatePageObjectDialog(project, "Create new Page Object class", "Enter new class name", "");
    }

    @Override
    protected String getActionDirectory() {
        return project.getComponent(BehatProject.class).getConfig()
            .getDefaultProfile().getPaths().getFeaturesDir();
    }

    @Override
    protected void onOk(DialogWrapper dialog) {
        try {
            PsiDirectory dir = getSelectedDirectory();

            PageObjectExtension extension = (PageObjectExtension) project.getComponent(BehatProject.class).getConfig()
                .getDefaultProfile().getExtension("PageObjects");

            Properties properties = new Properties();
            properties.setProperty("CLASS_NAME", ((CreateFeatureDialog) dialog).getName());
            properties.setProperty("NAMESPACE", extension.getPageNamespace());

            String fileName = ((CreatePageObjectDialog)dialog).getName();

            ApplicationManager.getApplication().runWriteAction(
                project.getComponent(BehatProject.class).getService(FileFactory.class)
                    .getCreateFileFromTemplateWriteAction(
                        dir.getVirtualFile(),
                        fileName,
                        PhpFileType.INSTANCE,
                        "Page Object.php",
                        properties
                    )
            );

        } catch (Exception e1) {
            Messages.showErrorDialog(project, "Failed creating page object.", "Failed Creating Page Object File.");
        }
    }
}
