package pl.projectspace.idea.plugins.php.behat.context;

import com.jetbrains.php.lang.psi.elements.MethodReference;
import pl.projectspace.idea.plugins.commons.php.code.completion.GenericMethodArgumentCompletionProvider;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.core.annotations.BehatNameProvider;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class GetSubContextNameProvider extends GenericMethodArgumentCompletionProvider
{
    @Override
    @BehatNameProvider
    protected List<String> getCompletions(MethodReference method) {
        return new LinkedList<String>(method.getProject().getComponent(BehatProject.class)
            .getService(ContextLocator.class).getMainContext().getSubContexts().keySet());
    }

    @Override
    protected boolean isEnabled() {
        return BehatProject.isEnabled();
    }
}