package pl.projectspace.idea.plugins.php.behat.code.type;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.DumbService;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.*;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import org.jetbrains.annotations.Nullable;
import pl.projectspace.idea.plugins.php.behat.psi.element.context.PageObjectContext;
import pl.projectspace.idea.plugins.php.behat.psi.element.page.PageObject;
import pl.projectspace.idea.plugins.php.behat.psi.utils.PsiUtils;
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

        PhpClass phpClass = PsiUtils.getClass(method);
        PsiElement[] parameters = method.getParameters();

        if (phpClass == null || !PageObjectContext.is(phpClass) || parameters.length != 1 || !(parameters[0] instanceof StringLiteralExpression)) {
            return null;
        }

        PageObjectLocator locator = ServiceManager.getService(psiElement.getProject(), PageObjectLocator.class);

        PageObject pageObject = null;

        if ((pageObject= locator.get(((StringLiteralExpressionImpl) parameters[0]).getContents())) == null) {
            return null;
        }

        return pageObject.getDecoratedObject().getFQN();
    }

}
