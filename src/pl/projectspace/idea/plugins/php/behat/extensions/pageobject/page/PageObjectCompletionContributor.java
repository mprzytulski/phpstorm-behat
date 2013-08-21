package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.jetbrains.php.lang.PhpLanguage;
import com.jetbrains.php.lang.psi.elements.MethodReference;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectCompletionContributor extends CompletionContributor {

    /**
     * Registry core contribution annotations
     */
    public PageObjectCompletionContributor() {
        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(LeafPsiElement.class)
                .withSuperParent(3, MethodReference.class)
                .withLanguage(PhpLanguage.INSTANCE),
            new PageObjectNameProvider()
        );
    }

}
