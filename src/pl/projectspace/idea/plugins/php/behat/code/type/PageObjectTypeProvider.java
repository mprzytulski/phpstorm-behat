package pl.projectspace.idea.plugins.php.behat.code.type;

import com.intellij.openapi.project.DumbService;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.*;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import org.jetbrains.annotations.Nullable;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.commons.php.service.locator.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.context.PageObjectContext;
import pl.projectspace.idea.plugins.php.behat.context.exceptions.InvalidReferenceMethodCall;
import pl.projectspace.idea.plugins.php.behat.page.PageObject;
import pl.projectspace.idea.plugins.php.behat.service.exceptions.InvalidMethodArgumentsException;
import pl.projectspace.idea.plugins.php.behat.service.exceptions.InvalidMethodNameResolveException;
import pl.projectspace.idea.plugins.php.behat.service.locator.PageObjectLocator;
import pl.projectspace.idea.plugins.php.behat.service.resolver.PageObjectResolver;
import pl.projectspace.idea.plugins.php.behat.service.resolver.SubContextResolver;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectTypeProvider extends AbstractClassTypeProvider {

    @Nullable
    @Override
    public String getType(PsiElement psiElement) {
        if (DumbService.isDumb(psiElement.getProject()) || !(psiElement instanceof MethodReference)) {
            return null;
        }

        try {
            PageObjectResolver service = psiElement.getProject().getComponent(BehatProject.class).getService(PageObjectResolver.class);

            return service.resolve(psiElement).getPageObject().getDecoratedObject().getFQN();
        } catch (InvalidReferenceMethodCall invalidReferenceMethodCall) {
            return null;
        } catch (InvalidMethodArgumentsException e) {
            return null;
        } catch (InvalidMethodNameResolveException e) {
            return null;
        }
    }

}
