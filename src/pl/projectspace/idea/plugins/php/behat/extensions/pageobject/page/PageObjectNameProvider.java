package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page;

import com.jetbrains.php.lang.psi.elements.MethodReference;
import pl.projectspace.idea.plugins.commons.php.code.completion.GenericMethodArgumentCompletionProvider;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.locator.PageObjectLocator;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectNameProvider extends GenericMethodArgumentCompletionProvider
{

    @Override
    protected List<String> getCompletions(MethodReference method) {
        return new LinkedList<String>(method.getProject().getComponent(BehatProject.class)
            .getService(PageObjectLocator.class).getAll().keySet());
    }

}
