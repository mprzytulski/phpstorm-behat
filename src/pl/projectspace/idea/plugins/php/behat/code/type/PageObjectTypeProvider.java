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
import pl.projectspace.idea.plugins.php.behat.page.PageObject;
import pl.projectspace.idea.plugins.php.behat.service.locator.PageObjectLocator;

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
            MethodReference method = (MethodReference)psiElement;

            PhpClass phpClass = PsiTreeUtils.getClass(method);

            BehatProject behatProject = phpClass.getProject().getComponent(BehatProject.class);

            if (!method.getName().equals("getPage")) {
                return null;
            }

            PsiElement[] parameters = method.getParameters();

            if (phpClass == null || !PageObjectContext.is(phpClass) || parameters.length != 1 || !(parameters[0] instanceof StringLiteralExpression)) {
                return null;
            }

            String name = ((StringLiteralExpressionImpl) parameters[0]).getContents();

            PageObject pageObject = behatProject.getService(PageObjectLocator.class).locate(name);

            return pageObject.getDecoratedObject().getFQN();
        } catch (MissingElementException e) {
            return null;
        }
    }

}
