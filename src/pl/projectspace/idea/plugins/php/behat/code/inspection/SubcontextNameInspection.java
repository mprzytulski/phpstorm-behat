package pl.projectspace.idea.plugins.php.behat.code.inspection;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.psi.element.context.BehatContext;
import pl.projectspace.idea.plugins.php.behat.psi.utils.PsiUtils;

/**
 * @author Daniel Ancuta <whisller@gmail.com>
 */
public class SubContextNameInspection extends LocalInspectionTool {

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new PageNameVisitor(holder);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public static final class PageNameVisitor extends PhpElementVisitor {

        private ProblemsHolder holder;

        public PageNameVisitor(ProblemsHolder holder) {
            this.holder = holder;
        }

        @Override
        public void visitPhpMethodReference(MethodReference reference) {
            PhpClass phpClass = PsiUtils.getClass(reference);
            PsiElement[] parameters = reference.getParameters();

            if (!BehatContext.isReferenceCall(phpClass, reference) || parameters.length != 1 || !(parameters[0] instanceof StringLiteralExpression)) {
                return;
            }

            String name = ((StringLiteralExpression) parameters[0]).getContents();
            BehatContext context = new BehatContext(phpClass);

            BehatContext subContext = context.getSubContext(name);
            if (subContext != null) {
                return;
            }

            holder.registerProblem(reference, "Invalid sub context reference name: \"" + name + "\"");
        }
    }
}
