package pl.projectspace.idea.plugins.php.behat.context;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.jetbrains.php.lang.PhpLanguage;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ContextReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar registrar) {
//        registrar.registerReferenceProvider(
//            PlatformPatterns.psiElement(GherkinStep.class),
//            new BehatStepReferenceProvider()
//        );

        registrar.registerReferenceProvider(
            PlatformPatterns.psiElement(StringLiteralExpression.class)
                .withLanguage(PhpLanguage.INSTANCE)
                .withParent(ParameterList.class),
            new GetSubContextReferenceProvider()
        );
    }
}
