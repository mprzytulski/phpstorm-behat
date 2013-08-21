package pl.projectspace.idea.plugins.php.behat.feature.step;

import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiSearchHelper;
import com.jetbrains.php.PhpIndex;
import org.jetbrains.plugins.cucumber.psi.GherkinFileType;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.core.BehatLocator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class StepLocator extends BehatLocator {

    private Project project;

    public StepLocator(Project project, BehatProject behat, PhpIndex index) {
        super(behat, index);
        this.project = project;
    }

    public <T> T[] find(String key) {
        T[] result = (T[])PsiSearchHelper.SERVICE.getInstance(project)
                .findCommentsContainingIdentifier(
                        key,
                        GlobalSearchScope.getScopeRestrictedByFileTypes(
                                GlobalSearchScope.allScope(project),
                                GherkinFileType.INSTANCE
                        )
                );

        return result;
    }

    @Override
    public <T> T locate(String key) throws MissingElementException {
        return null;
    }
}
