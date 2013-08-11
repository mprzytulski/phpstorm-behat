package pl.projectspace.idea.plugins.php.behat.code.completion.provider;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.psi.element.context.BehatContext;
import pl.projectspace.idea.plugins.php.behat.psi.element.lookup.SimpleTextLookup;
import pl.projectspace.idea.plugins.php.behat.psi.utils.PsiUtils;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class SubContextNameProvider extends CompletionProvider<CompletionParameters>
{

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
        PsiElement position = parameters.getPosition();

        MethodReference reference = (MethodReference)PsiTreeUtil.getParentOfType(position, MethodReference.class);
        PhpClass phpClass = PsiUtils.getClass(reference);

        if (!BehatContext.isReferenceCall(phpClass, reference)) {
            return;
        }

        BehatContext behatContext = new BehatContext(phpClass);

        for (String name : behatContext.getSubContexts().keySet()) {
            result.addElement(new SimpleTextLookup(name));
        }
    }
}
