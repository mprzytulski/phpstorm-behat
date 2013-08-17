package pl.projectspace.idea.plugins.php.behat.service.locator;

import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiSearchHelper;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.plugins.cucumber.psi.GherkinFileType;
import pl.projectspace.idea.plugins.commons.php.service.locator.BasePhpClassLocator;
import pl.projectspace.idea.plugins.php.behat.step.BehatStep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class StepLocator extends BasePhpClassLocator {

    public StepLocator(Project project, PhpIndex index) {
        super(project, index);
    }

    @Override
    public List<Class> getSupportedTypes() {
        return new ArrayList<Class>(Arrays.asList(BehatStep.class));
    }

    @Override
    public PhpClass locate(String key) {
        return null;
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
}
