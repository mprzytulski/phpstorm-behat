package pl.projectspace.idea.plugins.php.behat.code.inspection;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.psi.element.context.PageObjectContext;
import pl.projectspace.idea.plugins.php.behat.psi.element.context.page.PageObject;
import pl.projectspace.idea.plugins.php.behat.psi.utils.PsiUtils;
import pl.projectspace.idea.plugins.php.behat.service.locator.PageObjectLocator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectNameInspection extends LocalInspectionTool {

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new PageNameVisitor(holder);
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

            if (!PageObjectContext.isReferenceCall(phpClass, reference) || parameters.length != 1 || !(parameters[0] instanceof StringLiteralExpression)) {
                return;
            }

            String name = ((StringLiteralExpression) parameters[0]).getContents();

            PageObject page = ServiceManager.getService(reference.getProject(), PageObjectLocator.class).get(name);
            if (page != null) {
                return;
            }

            holder.registerProblem(reference, "Invalid PageObject reference name: " + name);
        }
    }
}
