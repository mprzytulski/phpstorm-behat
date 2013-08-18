package pl.projectspace.idea.plugins.php.behat.code.completion.provider.yaml;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.YAMLLanguage;
import org.jetbrains.yaml.YAMLTokenTypes;
import org.jetbrains.yaml.psi.YAMLDocument;
import pl.projectspace.idea.plugins.commons.php.psi.lookup.SimpleTextLookup;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class FileLevelCompletionProvider extends CompletionProvider<CompletionParameters> {
    @Override
    protected void addCompletions(@NotNull CompletionParameters completionParameters, ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {
        completionResultSet.addElement(new SimpleTextLookup("default"));
    }

    public static ElementPattern<PsiElement> getLocation() {
        return PlatformPatterns.psiElement()
            .withParent(
                PlatformPatterns.psiElement(YAMLDocument.class)
            )
            .withLanguage(YAMLLanguage.INSTANCE);
    }
}
