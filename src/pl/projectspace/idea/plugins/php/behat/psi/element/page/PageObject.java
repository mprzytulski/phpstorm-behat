package pl.projectspace.idea.plugins.php.behat.psi.element.page;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import pl.projectspace.idea.plugins.php.behat.psi.element.PhpClassDecorator;
import pl.projectspace.idea.plugins.php.behat.service.locator.PageObjectLocator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObject extends PhpClassDecorator {

    /**
     * Create PhpClass decorator
     *
     * @param phpClass
     */
    public PageObject(PhpClass phpClass) {
        super(phpClass);
    }

    public static boolean isValidPageCall(MethodReference element) {
        PsiElement[] parameters = element.getParameters();
        if (parameters.length != 1 || (!(parameters[0] instanceof StringLiteralExpression))) {
            return false;
        }

        PageObject page = ServiceManager.getService(element.getProject(), PageObjectLocator.class)
                .get(((StringLiteralExpression) parameters[0]).getContents());

        return (page != null);
    }
}
