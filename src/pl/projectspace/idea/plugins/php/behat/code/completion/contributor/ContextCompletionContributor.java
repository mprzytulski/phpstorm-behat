package pl.projectspace.idea.plugins.php.behat.code.completion.contributor;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.jetbrains.php.lang.documentation.phpdoc.lexer.PhpDocTokenTypes;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocPsiElement;
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocTag;
import com.jetbrains.php.lang.lexer.PhpTokenTypes;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import pl.projectspace.idea.plugins.php.behat.code.completion.provider.ContextAnnotationProvider;
import pl.projectspace.idea.plugins.php.behat.code.completion.provider.ContextNameProvider;
import pl.projectspace.idea.plugins.php.behat.code.completion.provider.PageNameProvider;

//import org.jetbrains.plugins.cucumber.psi.GherkinTokenTypes;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ContextCompletionContributor extends CompletionContributor {

    public ContextCompletionContributor() {

        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement().withParent(PhpDocComment.class),
            new ContextAnnotationProvider()
        );

        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(LeafPsiElement.class).withSuperParent(3, MethodReference.class),
            new ContextNameProvider()
        );

        extend(
            CompletionType.BASIC,
            PlatformPatterns.psiElement(LeafPsiElement.class).withSuperParent(3, MethodReference.class),
            new PageNameProvider()
        );
    }

}
