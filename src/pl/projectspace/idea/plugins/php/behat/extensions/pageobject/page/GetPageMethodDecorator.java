package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.PageObject;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.PageObjectContext;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.locator.PageObjectContextLocator;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.locator.PageObjectLocator;
import pl.projectspace.idea.plugins.php.behat.psi.element.MethodCallDecorator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class GetPageMethodDecorator extends MethodCallDecorator {
    protected PageObjectContext pageObjectContext;
    protected final PageObjectLocator pageObjectLocator;

    public GetPageMethodDecorator(PsiElement element) throws InvalidArgumentException, MissingElementException {
        super(element, "getPage");

        pageObjectContext = behatProject.getService(PageObjectContextLocator.class)
            .locate(getTarget().getFQN());

        pageObjectLocator = behatProject.getService(PageObjectLocator.class);
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

        return pageObjectLocator.locate(
            ((StringLiteralExpression) getParameter(0)).getContents()
        );
    }
}
