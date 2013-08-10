package pl.projectspace.idea.plugins.php.behat.code.reference.provider;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.PhpIndex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;
import pl.projectspace.idea.plugins.php.behat.psi.element.BehatStepImplementation;
import pl.projectspace.idea.plugins.php.behat.psi.referene.PhpMethodReference;
import pl.projectspace.idea.plugins.php.behat.psi.element.BehatContextClass;
import pl.projectspace.idea.plugins.php.behat.service.ContextLocator;

import java.util.ArrayList;
import java.util.Collection;

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
        PhpIndex index = PhpIndex.getInstance(element.getProject());

        ContextLocator locator = ServiceManager.getService(element.getProject(), ContextLocator.class);

        Collection<BehatContextClass> contextList = locator.getContextClasses();

        ArrayList<PsiReference> references = new ArrayList<PsiReference>();

        for (BehatContextClass behatContext : contextList) {
            BehatStepImplementation implementation = behatContext.getStepImplementation(step);
            if (implementation != null) {
                references.add(new PhpMethodReference(implementation.getMethod(), (PsiElement)implementation.getDefinition()));
                break;
            }
        }

        System.out.println(references.size());

        return references.toArray(new PsiReference[references.size()]);
    }
}
