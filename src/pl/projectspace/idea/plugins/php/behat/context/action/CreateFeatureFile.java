package pl.projectspace.idea.plugins.php.behat.context.action;

import com.intellij.ide.actions.OpenFileAction;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import org.jetbrains.plugins.cucumber.psi.GherkinFile;
import org.jetbrains.plugins.cucumber.psi.GherkinFileType;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.context.action.dialog.CreateFeatureDialog;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class CreateFeatureFile extends DirectoryAction {
    public void actionPerformed(AnActionEvent e) {
        CreateFeatureDialog dialog  = new CreateFeatureDialog(e.getProject());
        dialog.show();
        if (dialog.getExitCode() == DialogWrapper.OK_EXIT_CODE) {
            try {
                PsiDirectory dir = getSelectedDirectory(e);
                Properties properties = new Properties();
                properties.setProperty("FEATURE_TITLE", dialog.getName());

                StringBuffer sb = new StringBuffer();
                for (String line : dialog.getDescription().split("\n")) {
                    sb.append("  ");
                    sb.append(line);
                    sb.append("\n");
                }

                properties.setProperty("FEATURE_DESCRIPTION", sb.toString());

                ApplicationManager.getApplication().runWriteAction(new CreateGherkinFile(e.getProject(), dir, dialog.getFileName(), properties));

            } catch (Exception e1) {
                e1.printStackTrace();
                Messages.showErrorDialog(e.getProject(), "Failed creating feature file.", "Failed Creating Feature File.");
            }
        }
    }

    @Override
    public void update(AnActionEvent e) {
        String relativePath = getRelativeDirectory(e);

        String featuresDir = e.getProject().getComponent(BehatProject.class).getConfig()
            .getDefaultProfile().getPaths().getFeaturesDir();

        if (!featuresDir.startsWith("/")) {
            featuresDir = "/" + featuresDir;
        }

        if (relativePath == null || !featuresDir.equals(relativePath)) {
            e.getPresentation().setVisible(false);
        }
    }

    private static class CreateGherkinFile implements Runnable {

        private Project project;
        private PsiDirectory directory;
        private String file;
        private Properties properties;

        /**
         * Don't let anyone else instantiate this class
         */
        private CreateGherkinFile(Project project, PsiDirectory directory, String file, Properties properties) {
            this.project = project;
            this.directory = directory;
            this.file = file;
            this.properties = properties;
        }

        @Override
        public void run() {
            FileTemplate template = FileTemplateManager.getInstance().getInternalTemplate("Feature File.feature");
            String text;
            try {
                text = template.getText(properties);
                final PsiFile file = PsiFileFactory.getInstance(project).createFileFromText(this.file, GherkinFileType.INSTANCE, text);
                GherkinFile virtualFile = (GherkinFile) directory.add(file);

                OpenFileAction.openFile(virtualFile.getVirtualFile(), project);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
