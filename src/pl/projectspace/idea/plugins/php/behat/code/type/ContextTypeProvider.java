package pl.projectspace.idea.plugins.php.behat.code.type;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.jetbrains.php.lang.psi.elements.PhpNamedElement;
import com.jetbrains.php.lang.psi.resolve.types.PhpTypeProvider2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.context.exceptions.InvalidReferenceMethodCall;
import pl.projectspace.idea.plugins.php.behat.service.resolver.SubContextResolver;

import java.util.Collection;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ContextTypeProvider extends AbstractClassTypeProvider implements PhpTypeProvider2 {

    @Nullable
    @Override
    public String getType(PsiElement psiElement) {
        if (DumbService.isDumb(psiElement.getProject())) {
            return null;
        }

        try {
            SubContextResolver service = psiElement.getProject().getComponent(BehatProject.class).getService(SubContextResolver.class);

            return service.resolve(psiElement).getContext().getDecoratedObject().getFQN();
        } catch (InvalidReferenceMethodCall invalidReferenceMethodCall) {
            return null;
        }
    }

}
