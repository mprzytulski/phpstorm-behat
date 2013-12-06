package pl.projectspace.idea.plugins.php.behat.context;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.jetbrains.php.lang.psi.elements.MethodReference;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ContextCompletionContributor extends CompletionContributor {

    /**
     * Registry core completion providers
     */
    public ContextCompletionContributor() {
//        extend(
//            CompletionType.BASIC,
//            PlatformPatterns.psiElement().withParent(PhpDocComment.class),
//            new ContextAnnotationProvider()
//        );

        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(LeafPsiElement.class).withSuperParent(3, MethodReference.class),
            new GetSubContextNameProvider()
        );
    }

}
