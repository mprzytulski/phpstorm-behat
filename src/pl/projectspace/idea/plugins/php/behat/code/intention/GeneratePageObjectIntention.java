package pl.projectspace.idea.plugins.php.behat.code.intention;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.util.IncorrectOperationException;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.Parameter;
import com.jetbrains.php.lang.psi.resolve.types.PhpType;
import org.jetbrains.annotations.NotNull;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class GeneratePageObjectIntention implements IntentionAction {
    @NotNull
    @Override
    public String getText() {
        return "Create new PageObject Object class";
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return getText();
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
        PsiReference referenceAt = file.findReferenceAt(editor.getCaretModel().getOffset());
        if (!(referenceAt instanceof MethodReference)) {
            return false;
        }
//        if (!"compare".equals(((PsiReferenceExpression) referenceAt).getReferenceName())) {
//            return false;
//        }
        PsiElement target = referenceAt.resolve();
        if (!(target instanceof Method)) return false;
        Method method = (Method) target;

//        if (!GenerateAction.COM_GOOGLE_COMMON_COLLECT_COMPARISON_CHAIN.equals(method.getContainingClass().getQualifiedName())) {
//            return false;
//        }

        Parameter[] parameters = method.getParameters();

        return parameters.length == 1 && parameters[0].getType() == PhpType.STRING;
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
        PsiReference referenceAt = file.findReferenceAt(editor.getCaretModel().getOffset());
//        PsiElementFactory factory = JavaPsiFacade.getElementFactory(project);
//        PsiJavaCodeReferenceElement newReference = factory.createReferenceFromText("compareFalseFirst",
//                referenceAt.getElement());
//        ((PsiReferenceExpression) referenceAt).getReferenceNameElement().replace(newReference.getReferenceNameElement());
    }

    @Override
    public boolean startInWriteAction() {
        return true;
    }
}
