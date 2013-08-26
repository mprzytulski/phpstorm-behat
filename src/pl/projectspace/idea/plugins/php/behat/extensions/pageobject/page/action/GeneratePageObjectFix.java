package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.action;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.ide.actions.OpenFileAction;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.jetbrains.php.lang.PhpFileType;
import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.config.profile.extension.PageObjectExtension;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.action.CreatePageObjectFile;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.action.dialog.CreatePageObjectDialog;

import java.util.Properties;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class GeneratePageObjectFix implements LocalQuickFix {
    /**
     * PageObject class template
     */
    private static final String PAGE_OBJECT_TEMPLATE = "PageObject.php";
    private String pageObjectName;

    public GeneratePageObjectFix(String pageObjectName) {
        this.pageObjectName = pageObjectName;
    }

    @NotNull
    @Override
    public String getName() {
        return "Create new Page Object: '" + pageObjectName + "'";
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return getName();
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor problemDescriptor) {
        if (!(problemDescriptor.getPsiElement() instanceof StringLiteralExpression)) {
            return;
        }

        CreatePageObjectFile action = new CreatePageObjectFile();
        action.createFile(pageObjectName, problemDescriptor.getPsiElement().getProject());
    }
}
