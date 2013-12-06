package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.element;

import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.jetbrains.php.lang.psi.elements.Variable;
import com.jetbrains.php.lang.psi.elements.impl.MethodReferenceImpl;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.PageObject;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.locator.PageObjectLocator;
import pl.projectspace.idea.plugins.php.behat.psi.element.MethodCallDecorator;

import java.util.Arrays;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class GetElementMethodDecorator extends MethodCallDecorator {

    protected final PageObject pageObject;

    public GetElementMethodDecorator(PsiElement element) throws InvalidArgumentException, MissingElementException {
        super(element, Arrays.asList(new String[] { "getElement", "hasElement" }));

        MethodReferenceImpl method = PsiTreeUtil.getPrevSiblingOfType(element.getLastChild(), MethodReferenceImpl.class);

        Variable variable = null;
        try {
            variable = (Variable) element.getLastChild().getPrevSibling().getPrevSibling().getPrevSibling().getPrevSibling().getPrevSibling();
        } catch (NullPointerException e) {
        } catch (ClassCastException e) {
        }


        String pageName = null;
        if (variable != null && variable.getName().equalsIgnoreCase("this")) {
            PhpClass klass = PsiTreeUtil.getParentOfType(element, PhpClass.class);
            pageName = (klass != null) ? klass.getName() : null;
        } else if (method != null && method.getName().equalsIgnoreCase("getPage") && method.getParameters().length > 0) {
            pageName = ((StringLiteralExpression)(method.getParameters()[0])).getContents();
        }

        if (pageName != null) {
            this.pageObject = behatProject.getService(PageObjectLocator.class).locate(pageName);
        } else {
            pageObject = null;
        }
    }

    public PageObject getPageObject() {
        return pageObject;
    }

    @Override
    public PageElement getReturnType() throws MissingElementException {
        return (PageElement) super.getReturnType();
    }

    @Override
    protected Object resolveType() throws MissingElementException {
        if (pageObject == null || !hasParameter(0, StringLiteralExpression.class)) {
            throw new MissingElementException("Missing parameter");
        }

        return pageObject.getElement(
            ((StringLiteralExpression) getParameter(0)).getContents()
        );
    }
}
