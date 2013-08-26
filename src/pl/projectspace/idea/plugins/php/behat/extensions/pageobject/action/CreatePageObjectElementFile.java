package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.action;

import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.php.lang.PhpFileType;
import pl.projectspace.idea.plugins.commons.php.action.DirectoryAction;
import pl.projectspace.idea.plugins.commons.php.utils.FileFactory;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.config.profile.extension.PageObjectExtension;
import pl.projectspace.idea.plugins.php.behat.core.BehatDirectoryAction;
import pl.projectspace.idea.plugins.php.behat.core.annotations.BehatAction;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.action.dialog.CreatePageObjectDialog;

import java.util.Properties;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class CreatePageObjectElementFile extends BehatDirectoryAction {

    PageObjectExtension extension;

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        extension = (PageObjectExtension) anActionEvent.getProject().getComponent(BehatProject.class).getConfig()
            .getDefaultProfile().getExtension("PageObjects");
        super.actionPerformed(anActionEvent);
    }

    @Override
    protected DialogWrapper getDialog() {
        return new CreatePageObjectDialog(project, extension.getPageElementDirectory(), "Create new Page Object", "Enter Page Object name");
    }

    @Override
    protected String getActionDirectory() {
        return project.getComponent(BehatProject.class).getConfig()
            .getDefaultProfile().getPaths().getFeaturesDir();
    }

    @Override
    protected void onOk(DialogWrapper dialog) {
        try {
            createFile(((CreatePageObjectDialog) dialog).getName(), project);
        } catch (Exception e1) {
            Messages.showErrorDialog(project, "Failed creating page object element.", "Failed Creating Page Object Element File.");
        }
    }

    public void createFile(String className, Project project) {
        try {
            if (extension == null) {
                extension = ((PageObjectExtension) project.getComponent(BehatProject.class).getConfig()
                    .getDefaultProfile().getExtension("PageObjects"));
            }

            Properties properties = FileTemplateManager.getInstance().getDefaultProperties();
            properties.setProperty("CLASS_NAME", className);
            properties.setProperty("NAMESPACE", extension.getElementNamespace());

            ApplicationManager.getApplication().runWriteAction(
                project.getComponent(BehatProject.class).getService(FileFactory.class)
                    .getCreateFileFromTemplateWriteAction(
                        extension.getPageElementDirectory(),
                        className + ".php",
                        PhpFileType.INSTANCE,
                        "Page Object Element.php",
                        properties
                    )
            );
        } catch (Exception e1) {
            Messages.showErrorDialog(project, "Failed creating page object element.", "Failed Creating Page Object Element File.");
        }
    }

}
