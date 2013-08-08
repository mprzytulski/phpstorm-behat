package pl.projectspace.idea.plugins.php.behat.code.reference.provider;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocTag;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;
import pl.projectspace.idea.plugins.php.behat.code.reference.PhpClassReference;
import pl.projectspace.idea.plugins.php.behat.psi.BehatStepDefinition;
import pl.projectspace.idea.plugins.php.behat.psi.element.BehatContextClass;
import pl.projectspace.idea.plugins.php.behat.service.ContextLocator;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatStepScenarioReferenceProvider extends PsiReferenceProvider {
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        if (!(element instanceof GherkinStep)) {
            return new PsiReference[0];
        }

        GherkinStep step = (GherkinStep) element;
        PhpIndex index = PhpIndex.getInstance(element.getProject());

        ContextLocator locator = ServiceManager.getService(step.getProject(), ContextLocator.class);

        Collection<BehatContextClass> contextList = locator.getContextClasses(step.getProject());
//        Collection<PhpClass> contextList = index.getClassesByFQN("\\TestContext");

        ArrayList<PsiReference> references = new ArrayList<PsiReference>();

//        for (BehatContextClass behatContext : contextList) {
//
//            for (BehatStepDefinition behatStep : behatContext.getStepDefinitions()) {
//                if (behatStep.isImplementationOf(step)) {
//                    references.add(new PhpClassReference((PsiElement) behatContext.getPhpClass(), behatContext.getPhpClass().getFQN()));
//                }
//            }
//        }

//        references.add(element);

        System.out.println(references.size());

        return references.toArray(new PsiReference[references.size()]);
    }
}
