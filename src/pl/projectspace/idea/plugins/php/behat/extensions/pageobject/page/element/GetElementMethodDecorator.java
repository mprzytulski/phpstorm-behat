package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.element;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.PageObject;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.locator.PageObjectLocator;
import pl.projectspace.idea.plugins.php.behat.psi.element.MethodCallDecorator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class GetElementMethodDecorator extends MethodCallDecorator {

    protected final PageObject pageObject;

    public GetElementMethodDecorator(PsiElement element) throws InvalidArgumentException, MissingElementException {
        super(element, "getElement");

        pageObject = behatProject.getService(PageObjectLocator.class)
            .locate(getTarget().getFQN());
    }

    @Override
    public PageObject getReturnType() throws MissingElementException {
        return (PageObject) super.getReturnType();
    }

    @Override
    protected Object resolveType() throws MissingElementException {
        if (!hasParameter(0)) {
            throw new MissingElementException("Missing parameter");
        }

        return pageObject.getElement(
            ((StringLiteralExpression) getParameter(0)).getContents()
        );
    }
}
