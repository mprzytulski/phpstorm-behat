package pl.projectspace.idea.plugins.php.behat.core.reference.provider;

import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.GlobalSearchScopes;
import com.intellij.psi.search.PsiSearchHelper;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.step.BehatStep;
import pl.projectspace.idea.plugins.php.behat.psi.reference.BehatStepReference;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatStepReferenceProvider extends PsiReferenceProvider {
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        if (!(element instanceof GherkinStep)) {
            return new PsiReference[0];
        }

        GherkinStep step = (GherkinStep) (element);

        String[] phrases = step.getSubstitutedName().replaceAll("\"[^\"]*\"", "\"\"").split("\"");
        int maxLen = 0;
        String longest = "";
        for (String phrase : phrases) {
            if (phrase.length() > maxLen) {
                longest = phrase;
                maxLen = phrase.length();
            }
        }

        BehatProject project = ((BehatProject)element.getProject().getComponent("BehatProject"));

        String dirPath = project.getConfig().getDefaultProfile().getPaths().getFeaturesDir();

        VirtualFile featuresDirectory = element.getProject().getBaseDir().findFileByRelativePath(dirPath);

        GlobalSearchScope scope = GlobalSearchScopes.directoryScope(element.getProject(), featuresDirectory, true);

        PsiElement[] result = PsiSearchHelper.SERVICE.getInstance(element.getProject())
                .findCommentsContainingIdentifier(longest, scope);


        Map<String, PsiReference> references = new HashMap<String, PsiReference>();

        for (PsiElement el : result) {
            if (BehatStep.isImplementationOf((PhpDocComment)el, step)) {
                BehatStep behatStep = new BehatStep((PhpDocComment) el, step);
                if (!references.containsKey(behatStep.getMethod().getFQN())) {
                    references.put(behatStep.getMethod().getFQN(), new BehatStepReference(behatStep, (PsiElement)step));
                }
            }
        }

        return references.values().toArray(new PsiReference[references.size()]);
    }
}
