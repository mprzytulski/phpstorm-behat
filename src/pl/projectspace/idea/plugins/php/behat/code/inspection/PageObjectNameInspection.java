package pl.projectspace.idea.plugins.php.behat.code.inspection;

import com.intellij.codeInspection.*;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiReference;
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
import pl.projectspace.idea.plugins.php.behat.context.exceptions.InvalidReferenceMethodCall;
import pl.projectspace.idea.plugins.php.behat.page.PageObject;
import pl.projectspace.idea.plugins.php.behat.service.exceptions.InvalidMethodArgumentsException;
import pl.projectspace.idea.plugins.php.behat.service.locator.PageObjectLocator;
import pl.projectspace.idea.plugins.php.behat.service.resolver.PageObjectResolver;

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
        public void visitPhpMethodReference(MethodReference psiElement) {
            try {
                PageObjectResolver service = psiElement.getProject().getComponent(BehatProject.class).getService(PageObjectResolver.class);
                service.resolve(psiElement);
            } catch (InvalidReferenceMethodCall e) {
                PsiElement[] parameters = psiElement.getParameters();
                holder.registerProblem(parameters[0], "Invalid Page Object class name", new GeneratePageObjectFix());
            } catch (InvalidMethodArgumentsException e) {
                return;
            }
        }
    }
}
