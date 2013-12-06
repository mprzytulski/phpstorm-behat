package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.element;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.commons.php.code.completion.GenericMethodArgumentCompletionProvider;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.commons.php.utils.annotation.DependsOnPlugin;
import pl.projectspace.idea.plugins.commons.php.utils.annotation.RequireMethod;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.PageObject;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.locator.PageElementLocator;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.locator.PageObjectLocator;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
@DependsOnPlugin("behat")
@RequireMethod("getElement")
public class ElementNameProvider extends GenericMethodArgumentCompletionProvider
{
    @Override
    protected List<String> getCompletions(MethodReference method) {
        LinkedList<String> list = new LinkedList<String>(method.getProject().getComponent(BehatProject.class)
                .getService(PageElementLocator.class).getAll().keySet());

        try {
            GetElementMethodDecorator methodDecorator = new GetElementMethodDecorator(method);
            list.addAll(methodDecorator.getPageObject().getElements().keySet());
        } catch (MissingElementException e) {
        } catch (InvalidArgumentException e) {
        }

        return list;
    }
}
