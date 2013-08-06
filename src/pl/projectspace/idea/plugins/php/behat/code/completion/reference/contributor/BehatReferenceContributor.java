package pl.projectspace.idea.plugins.php.behat.code.completion.reference.contributor;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.plugins.cucumber.psi.GherkinTokenTypes;
import pl.projectspace.idea.plugins.php.behat.code.completion.reference.provider.BehatStepScenarioReferenceProvider;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar registrar) {
        System.out.println("registry");

        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(),
            new BehatStepScenarioReferenceProvider()
        );
    }
}
