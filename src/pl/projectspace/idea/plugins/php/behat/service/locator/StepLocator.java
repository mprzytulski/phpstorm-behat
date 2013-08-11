package pl.projectspace.idea.plugins.php.behat.service.locator;

import com.intellij.psi.PsiElement;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.PsiSearchHelper;
import org.jetbrains.plugins.cucumber.psi.GherkinFileType;
import pl.projectspace.idea.plugins.php.behat.service.ProjectRelatedService;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class StepLocator extends ProjectRelatedService {
    @Override
    public void configure() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public PsiElement[] findDefinition(String text) {
        PsiElement[] result = PsiSearchHelper.SERVICE.getInstance(project)
            .findCommentsContainingIdentifier(
                text,
                GlobalSearchScope.getScopeRestrictedByFileTypes(
                    GlobalSearchScope.allScope(project),
                    GherkinFileType.INSTANCE
                )
            );

        return result;
    }
}
