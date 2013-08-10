package pl.projectspace.idea.plugins.php.behat.code.inspection;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.elements.*;
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.service.ContextLocator;

/**
 * @author Daniel Ancuta <whisller@gmail.com>
 */
public class SubcontextNameInspection extends LocalInspectionTool {

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
        public void visitPhpMethodReference(MethodReference method) {
            PsiElement[] parameters = method.getParameters();

            ContextLocator locator = ServiceManager.getService(method.getProject(), ContextLocator.class);

            if (!method.getName().equalsIgnoreCase("getSubcontext") || parameters.length != 1) {
                return;
            }

            if (!(parameters[0] instanceof StringLiteralExpression)) {
                return;
            }

            String name = ((StringLiteralExpression) parameters[0]).getContents();

            PhpClass subcontext = locator.getSubContextFor(
                locator.getMainContextFor(PsiTreeUtil.getParentOfType(method, PhpClass.class)),
                name
            );

            if (subcontext != null) {
                return;
            }

            holder.registerProblem(method, "Invalid subcontext reference name: " + name);
        }
    }
}
