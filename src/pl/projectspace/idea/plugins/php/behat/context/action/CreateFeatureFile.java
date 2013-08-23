package pl.projectspace.idea.plugins.php.behat.context.action;

import com.intellij.ide.actions.OpenFileAction;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import org.jetbrains.plugins.cucumber.psi.GherkinFile;
import org.jetbrains.plugins.cucumber.psi.GherkinFileType;
import pl.projectspace.idea.plugins.commons.php.action.DirectoryAction;
import pl.projectspace.idea.plugins.commons.php.utils.FileFactory;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.context.action.dialog.CreateFeatureDialog;
import pl.projectspace.idea.plugins.php.behat.context.action.dialog.TestDialog;
import pl.projectspace.idea.plugins.php.behat.core.BehatDirectoryAction;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class CreateFeatureFile extends BehatDirectoryAction {

    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        project = anActionEvent.getProject();

        CreateFeatureDialog dialog = new CreateFeatureDialog(project);
        dialog.show();

//        if (dialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
//            onOk(dialog);
//        }
    }

    @Override
    protected DialogWrapper getDialog() {
        return new CreateFeatureDialog(project);
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
            Properties properties = new Properties();
            properties.setProperty("FEATURE_TITLE", ((CreateFeatureDialog) dialog).getName());

            StringBuffer sb = new StringBuffer();
            for (String line : ((CreateFeatureDialog)dialog).getDescription().split("\n")) {
                sb.append("  ");
                sb.append(line);
                sb.append("\n");
            }

            properties.setProperty("FEATURE_DESCRIPTION", sb.toString());

            ApplicationManager.getApplication().runWriteAction(
                project.getComponent(BehatProject.class).getService(FileFactory.class)
                    .getCreateFileFromTemplateWriteAction(
                        dir.getVirtualFile(),
                        ((CreateFeatureDialog)dialog).getFileName(),
                        GherkinFileType.INSTANCE,
                        "Feature File.feature",
                        properties
                    )
            );

        } catch (Exception e1) {
            Messages.showErrorDialog(project, "Failed creating feature file.", "Failed Creating Feature File.");
        }
    }
}
