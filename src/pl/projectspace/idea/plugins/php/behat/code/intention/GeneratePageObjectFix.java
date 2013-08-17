package pl.projectspace.idea.plugins.php.behat.code.intention;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.ide.actions.OpenFileAction;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.components.ServiceManager;
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
import pl.projectspace.idea.plugins.php.behat.service.locator.PageObjectLocator;

import java.util.Properties;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class GeneratePageObjectFix implements LocalQuickFix {
    /**
     * PageObject class template
     */
    private static final String PAGE_OBJECT_TEMPLATE = "PageObject.php";

    @NotNull
    @Override
    public String getName() {
        return "Create new Page Object class";
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
        PhpFile phpFile = (PhpFile) createFile(project, ((StringLiteralExpression) problemDescriptor.getPsiElement()).getContents());
        OpenFileAction.openFile(phpFile.getVirtualFile(), project);
    }

    private PsiElement createFile(Project project, String className) {
        String fileName = className + '.' + PhpFileType.INSTANCE.getDefaultExtension();

        BehatProject behatProject = ((BehatProject)project.getComponent("BehatProject"));

        Properties p = new Properties();
        final FileTemplate template = FileTemplateManager.getInstance().getJ2eeTemplate(PAGE_OBJECT_TEMPLATE);

        Properties properties = new Properties();
        properties.put("CLASS_NAME", className);

        String text;
        try {
            text = template.getText(properties);
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to load template for " + FileTemplateManager.getInstance().internalTemplateToSubject(PAGE_OBJECT_TEMPLATE), e);
        }

        VirtualFile featureFileDirectory = behatProject.getConfiguration().getBehatContextBaseDirectory();
        
        final PsiFileFactory factory = PsiFileFactory.getInstance(project);
        final PsiFile file = factory.createFileFromText(fileName, PhpFileType.INSTANCE, text);

        PsiDirectory directory = file.getManager().findDirectory(featureFileDirectory);

        return directory.add(file);
    }
}
