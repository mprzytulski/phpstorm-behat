package pl.projectspace.idea.plugins.php.behat.page;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.*;
import pl.projectspace.idea.plugins.commons.php.psi.element.PhpClassDecorator;
import pl.projectspace.idea.plugins.commons.php.service.locator.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.service.locator.PageObjectLocator;

import java.util.HashMap;
import java.util.Map;

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

    public Map<String, PsiElement> getElementLocators() {
        Field elements = getDecoratedObject().findFieldByName("elements", false);
        Map<String, PsiElement> map = new HashMap<String, PsiElement>();

        if (elements == null || (elements.getChildren().length == 0)) {
            return map;
        }

        PsiElement[] arrayElements = elements.getChildren()[0].getChildren();

        if (arrayElements.length == 0) {
            return map;
        }

        for (PsiElement element : arrayElements) {
            StringLiteralExpression el = (StringLiteralExpression) element.getFirstChild().getFirstChild();
            String key = el.getContents();
            map.put(key, element);
        }

        return map;
    }

}
