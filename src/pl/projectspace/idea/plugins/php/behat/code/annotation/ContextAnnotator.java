package pl.projectspace.idea.plugins.php.behat.code.annotation;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocTag;
import com.jetbrains.php.lang.psi.elements.Method;
import org.jetbrains.annotations.NotNull;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ContextAnnotator implements Annotator {
    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if(!PlatformPatterns.psiElement(PhpDocTag.class).accepts(element)) {
            return;
        }

        if (element instanceof PhpDocTag) {

            PhpDocTag phpDocTag = (PhpDocTag) element;

            String docTagName = phpDocTag.getName();
            if(docTagName == null || !docTagName.equals("@BeforeSuite") || !phpDocTag.getTagValue().equals("()")) {
                return;
            }

            PhpDocComment docComment = PsiTreeUtil.getParentOfType(element, PhpDocComment.class);
            if(null == docComment) {
                return;
            }

            Method method = PsiTreeUtil.getNextSiblingOfType(docComment, Method.class);
            if(null == method || !method.getName().endsWith("Action")) {
                return;
            }

//            String shortcutName = TwigUtil.getControllerMethodShortcut(method);
//            if(shortcutName == null) {
//                return;
//            }

//            Map<String, TwigFile> twigFilesByName = TwigHelper.getTwigFilesByName(element.getProject());
//            TwigFile twigFile = twigFilesByName.get(shortcutName);
//
//            if (null != twigFile) {
//                return;
//            }

//            if(null != element.getFirstChild()) {
//                holder.createWarningAnnotation(element.getFirstChild().getTextRange(), "Create Template")
//                        .registerFix(new CreatePropertyQuickFix(method));
//            }

        }
    }
}
