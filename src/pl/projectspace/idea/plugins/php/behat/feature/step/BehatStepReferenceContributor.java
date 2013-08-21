package pl.projectspace.idea.plugins.php.behat.feature.step;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatStepReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(GherkinStep.class),
            new BehatStepReferenceProvider()
        );
    }

}
