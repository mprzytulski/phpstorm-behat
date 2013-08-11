package pl.projectspace.idea.plugins.php.behat.code.intention;

import com.intellij.codeInsight.daemon.impl.quickfix.QuickFixAction;
import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.codeInsight.template.Template;
import com.intellij.codeInsight.template.TemplateManager;
import com.intellij.codeInsight.template.impl.TemplateManagerImpl;
import com.intellij.codeInsight.template.impl.TemplateState;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.find.FindModel;
import com.intellij.find.impl.FindInProjectUtil;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.impl.file.PsiDirectoryFactory;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.ObjectUtils;
import com.jetbrains.php.lang.PhpFileType;
import com.jetbrains.php.lang.psi.PhpCodeEditUtil;
import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.PhpPsiElementFactory;
import com.jetbrains.php.lang.psi.PhpPsiUtil;
import com.jetbrains.php.lang.psi.elements.*;
import com.jetbrains.php.lang.psi.resolve.types.PhpType;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.psi.element.context.PageObjectContext;
import pl.projectspace.idea.plugins.php.behat.psi.element.page.PageObject;
import pl.projectspace.idea.plugins.php.behat.service.locator.PageObjectLocator;

import java.util.Properties;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class GeneratePageObjectFix implements LocalQuickFix {

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
    }

    private PsiElement createFile(Project project, String className) {
        String fileName = className + '.' + PhpFileType.INSTANCE.getDefaultExtension();

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

        VirtualFile featureFileDirectory = ServiceManager.getService(project, PageObjectLocator.class).getBaseDir();
        
        final PsiFileFactory factory = PsiFileFactory.getInstance(project);
        final PsiFile file = factory.createFileFromText(fileName, PhpFileType.INSTANCE, text);

        PsiDirectory directory = file.getManager().findDirectory(featureFileDirectory);

        return directory.add(file);
    }
}
