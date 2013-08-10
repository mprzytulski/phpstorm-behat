package pl.projectspace.idea.plugins.php.behat.code.reference.contributor;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.jetbrains.php.lang.PhpLanguage;
import com.jetbrains.php.lang.psi.elements.Parameter;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;
import org.jetbrains.plugins.cucumber.psi.GherkinTokenTypes;
import pl.projectspace.idea.plugins.php.behat.code.reference.provider.BehatStepScenarioReferenceProvider;
import pl.projectspace.idea.plugins.php.behat.code.reference.provider.PageObjectReferenceProvider;
import pl.projectspace.idea.plugins.php.behat.code.reference.provider.SubcontextReferenceProvider;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(GherkinStep.class),
            new BehatStepScenarioReferenceProvider()
        );

        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(StringLiteralExpression.class)
                .withLanguage(PhpLanguage.INSTANCE)
                .withParent(ParameterList.class),
            new PageObjectReferenceProvider()
        );

        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(StringLiteralExpression.class)
                .withLanguage(PhpLanguage.INSTANCE)
                .withParent(ParameterList.class),
            new SubcontextReferenceProvider()
        );
    }
}
