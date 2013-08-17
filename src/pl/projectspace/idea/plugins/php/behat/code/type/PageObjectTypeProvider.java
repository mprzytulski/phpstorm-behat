package pl.projectspace.idea.plugins.php.behat.code.type;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.DumbService;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.*;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import org.jetbrains.annotations.Nullable;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.behat.context.PageObjectContext;
import pl.projectspace.idea.plugins.php.behat.behat.page.PageObject;
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

        MethodReference method = (MethodReference)psiElement;
        if (!method.getName().equals("getPage")) {
            return null;
        }

        PhpClass phpClass = PsiTreeUtils.getClass(method);
        PsiElement[] parameters = method.getParameters();

        BehatProject behatProject = (BehatProject)phpClass.getProject().getComponent("BehatProject");

        if (phpClass == null || !PageObjectContext.is(phpClass) || parameters.length != 1 || !(parameters[0] instanceof StringLiteralExpression)) {
            return null;
        }

        PageObject pageObject = null;

        String name = ((StringLiteralExpressionImpl) parameters[0]).getContents();

        if ((pageObject= behatProject.getLocator().locate(name, PageObject.class)) == null) {
            return null;
        }

        return pageObject.getDecoratedObject().getFQN();
    }

}
