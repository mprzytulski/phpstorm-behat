package pl.projectspace.idea.plugins.php.behat.code.generator;

import com.intellij.codeInsight.template.*;
import com.intellij.codeInsight.template.impl.ConstantNode;
import com.intellij.codeInsight.template.impl.TemplateManagerImpl;
import com.intellij.codeInsight.template.impl.TemplateState;
import com.intellij.ide.fileTemplates.FileTemplate;
import com.intellij.ide.fileTemplates.FileTemplateManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ObjectUtils;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.PhpFileType;
import com.jetbrains.php.lang.psi.PhpCodeEditUtil;
import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.PhpPsiElementFactory;
import com.jetbrains.php.lang.psi.PhpPsiUtil;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.templates.PhpFileTemplateUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.cucumber.StepDefinitionCreator;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;

import java.io.IOException;
import java.util.Properties;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatStepCreator implements StepDefinitionCreator {

    public static final String STEP_DEFINITION_TEMPLATE = "BehatContext.php";

    @NotNull
    @Override
    public PsiFile createStepDefinitionContainer(@NotNull PsiDirectory dir, @NotNull String name) {
        String fileName = name + '.' + PhpFileType.INSTANCE.getDefaultExtension();

        Properties p = new Properties();
        final FileTemplate template = FileTemplateManager.getInstance().getJ2eeTemplate(STEP_DEFINITION_TEMPLATE);

        Properties properties = new Properties();
        properties.put("CLASS_NAME", name);

        String text;
        try {
            text = template.getText(properties);
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to load template for " + FileTemplateManager.getInstance().internalTemplateToSubject(STEP_DEFINITION_TEMPLATE), e);
        }

        final PsiFileFactory factory = PsiFileFactory.getInstance(dir.getProject());
        final PsiFile file = factory.createFileFromText(fileName, PhpFileType.INSTANCE, text);

        return (PsiFile)dir.add(file);
    }

    @Override
    public boolean createStepDefinition(@NotNull GherkinStep step, @NotNull PsiFile file) {
        if (!(file instanceof PhpFile)) return false;

        final Project project = file.getProject();
        final VirtualFile vFile = ObjectUtils.assertNotNull(file.getVirtualFile());
        final OpenFileDescriptor descriptor = new OpenFileDescriptor(project, vFile);
        FileEditorManager.getInstance(project).getAllEditors(vFile);
        FileEditorManager.getInstance(project).openTextEditor(descriptor, true);
        final Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();

        if (editor != null) {
            final TemplateManager templateManager = TemplateManager.getInstance(file.getProject());
            final TemplateState templateState = TemplateManagerImpl.getTemplateState(editor);
            final Template template = templateManager.getActiveTemplate(editor);
            if (templateState != null && template != null) {
                templateState.gotoEnd();
            }
        }

        PhpClass aClass = null;
        aClass = PhpPsiUtil.findClass((PhpFile)file, Condition.TRUE);

        Method method = PhpPsiElementFactory.createMethod(project, "/** */\npublic function testFoo(){}");
        assert (aClass != null);
        PsiElement element = PhpCodeEditUtil.insertClassMember(aClass, method);
        PsiDocumentManager.getInstance(project).doPostponedOperationsAndUnblockDocument(editor.getDocument());
        TextRange range = element.getTextRange();
        editor.getDocument().replaceString(range.getStartOffset(), range.getEndOffset(), "");
        editor.getCaretModel().moveToOffset(range.getStartOffset());

//        Expression nameExpr = new ConstantNode(StringUtil.notNullize(testMethodName));
//        template.addVariable("name", nameExpr, nameExpr, true);
//        template.addTextSegment("(){\n");
//        template.addEndVariable();
//        template.addTextSegment("}");
//        template.setToIndent(true);
//        template.setToReformat(true);
//        template.setToShortenLongNames(true);
//        TemplateManager.getInstance(project).startTemplate(editor, template, null);

        return true;
    }

    @Override
    public boolean validateNewStepDefinitionFileName(@NotNull Project project, @NotNull String s) {
        // @todo - check if this is proper file name
        return true;
    }

    @NotNull
    @Override
    public PsiDirectory getDefaultStepDefinitionFolder(@NotNull GherkinStep gherkinStep) {
        final PsiFile featureFile = gherkinStep.getContainingFile();

        return ObjectUtils.assertNotNull(featureFile.getParent().findSubdirectory("bootstrap"));
    }

    @NotNull
    @Override
    public String getStepDefinitionFilePath(@NotNull PsiFile file) {
        final VirtualFile vFile = file.getVirtualFile();
        if (file instanceof PhpFile && vFile != null) {
            return vFile.getNameWithoutExtension();
//            String packageName = ((PhpFile)file).getPackageName();
//            if (StringUtil.isEmptyOrSpaces(packageName)) {
//                return vFile.getNameWithoutExtension();
//            }
//            else {
//                return packageName + "." + vFile.getNameWithoutExtension();
//            }
        }
        return file.getName();
    }
}
