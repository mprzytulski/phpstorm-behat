package pl.projectspace.idea.plugins.php.behat.code.reference.contributor;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.jetbrains.php.lang.PhpLanguage;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;
import pl.projectspace.idea.plugins.php.behat.code.reference.provider.BehatStepReferenceProvider;
import pl.projectspace.idea.plugins.php.behat.code.reference.provider.PageObjectElementGettersReferenceProvider;
import pl.projectspace.idea.plugins.php.behat.code.reference.provider.PageObjectReferenceProvider;
import pl.projectspace.idea.plugins.php.behat.code.reference.provider.SubContextReferenceProvider;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(GherkinStep.class),
            new BehatStepReferenceProvider()
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
                new PageObjectElementGettersReferenceProvider()
        );

        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(StringLiteralExpression.class)
                .withLanguage(PhpLanguage.INSTANCE)
                .withParent(ParameterList.class),
            new SubContextReferenceProvider()
        );
    }
}
