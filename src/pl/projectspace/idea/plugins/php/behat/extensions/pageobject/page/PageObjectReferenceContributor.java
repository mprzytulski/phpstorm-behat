package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiReferenceContributor;
import com.intellij.psi.PsiReferenceRegistrar;
import com.jetbrains.php.lang.PhpLanguage;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.PageObjectReferenceProvider;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.element.ElementReferenceProvider;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.element.PageElement;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectReferenceContributor extends PsiReferenceContributor {

    @Override
    public void registerReferenceProviders(PsiReferenceRegistrar registrar) {
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
            new ElementReferenceProvider()
        );
    }
}
