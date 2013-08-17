package pl.projectspace.idea.plugins.php.behat.code.inspection;

import com.intellij.codeInspection.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.commons.php.service.locator.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.code.intention.GeneratePageObjectFix;
import pl.projectspace.idea.plugins.php.behat.context.PageObjectContext;
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
            if (!PageObjectContext.isGetPageCallOn(reference)) {
                return;
            }

            PhpClass phpClass = PsiTreeUtils.getClass(reference);
            PsiElement[] parameters = reference.getParameters();

            if (!PageObjectContext.is(phpClass) || parameters.length != 1 || !(parameters[0] instanceof StringLiteralExpression)) {
                return;
            }

            String name = ((StringLiteralExpression) parameters[0]).getContents();

            try {
                reference.getProject().getComponent(BehatProject.class).getService(PageObjectLocator.class).locate(name);
            } catch (MissingElementException e) {
                return;
            }

            holder.registerProblem(parameters[0], "Invalid Page Object class name", new GeneratePageObjectFix());
        }
    }
}
