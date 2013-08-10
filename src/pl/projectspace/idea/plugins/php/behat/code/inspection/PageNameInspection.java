package pl.projectspace.idea.plugins.php.behat.code.inspection;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.impl.PsiParameterizedCachedValue;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.Parameter;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.jetbrains.php.lang.psi.elements.impl.ParameterImpl;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.service.PageObjectLocator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageNameInspection extends LocalInspectionTool {

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
            PsiElement[] parameters = reference.getParameters();

            PageObjectLocator locator = ServiceManager.getService(reference.getProject(), PageObjectLocator.class);

            if (!reference.getName().equalsIgnoreCase("getPage") || parameters.length != 1) {
                return;
            }

            if (!(parameters[0] instanceof StringLiteralExpression)) {
                return;
            }

            String name = ((StringLiteralExpression) parameters[0]).getContents();

            if (locator.isPage(name)) {
                return;
            }

            holder.registerProblem(reference, "Invalid PageObject reference name: " + name);
        }
    }
}
