package pl.projectspace.idea.plugins.php.behat.extensions.pageobject;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.Field;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import pl.projectspace.idea.plugins.commons.php.psi.element.PhpClassDecorator;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.commons.php.utils.PhpStringUtils;
import pl.projectspace.idea.plugins.php.behat.core.BehatProjectPhpClass;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.locator.PageElementLocator;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.element.PageElement;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObject extends BehatProjectPhpClass {

    /**
     * Create PhpClass decorator
     *
     * @param phpClass
     */
    public PageObject(PhpClass phpClass) {
        super(phpClass);
    }

    @Override
    public boolean hasRelatedClass() {
        return false;
    }

    public PageElement getElement(String name) throws MissingElementException {
        Map<String, PageElement> all = getElements();

        if (!all.containsKey(name)) {
            PageElement el = behat.getService(PageElementLocator.class).locate(name);
            if (el == null) {
                throw new MissingElementException("Failed to locate named element");
            }

            return el;
        }

        return all.get(name);
    }

    public Map<String, PageElement> getElements() {
        return getInlineElements();
    }

    protected Map<String, PageElement> getInlineElements() {
        Field elements = getDecoratedObject().findFieldByName("elements", false);
        Map<String, PageElement> map = new HashMap<String, PageElement>();

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
            map.put(key, new PageElement(element));
        }

        return map;
    }

}
