package pl.projectspace.idea.plugins.php.behat.code.inspection;

import com.intellij.codeInspection.LocalInspectionTool;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.visitors.PhpElementVisitor;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.context.exceptions.InvalidReferenceMethodCall;
import pl.projectspace.idea.plugins.php.behat.service.resolver.SubContextResolver;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class SubContextNameInspection extends LocalInspectionTool {

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
        public void visitPhpMethodReference(MethodReference methodReference) {
            try {
                methodReference.getProject()
                    .getComponent(BehatProject.class).getService(SubContextResolver.class).resolve(methodReference);
            } catch (InvalidReferenceMethodCall invalidReferenceMethodCall) {
                holder.registerProblem(methodReference, "Invalid sub context reference name: \"" + methodReference.getName() + "\"");
            }
        }
    }
}
