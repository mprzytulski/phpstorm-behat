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
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.commons.php.psi.lookup.SimpleTextLookup;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.context.BehatContext;
import pl.projectspace.idea.plugins.php.behat.page.PageObject;
import pl.projectspace.idea.plugins.php.behat.service.locator.BehatContextLocator;
import pl.projectspace.idea.plugins.php.behat.service.validator.BehatContextValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class SubContextNameProvider extends CompletionProvider<CompletionParameters>
{

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
        PsiElement position = parameters.getPosition();

        MethodReference reference = PsiTreeUtil.getParentOfType(position, MethodReference.class);

        BehatContextValidator validator = reference.getProject()
                .getComponent(BehatProject.class).getService(BehatContextValidator.class);

        PhpClass phpClass = reference.getProject()
                .getComponent(BehatProject.class).getService(PsiTreeUtils.class).getClass(reference);

        if (validator.isBehatContext(phpClass)) {
            return;
        }

        BehatContext behatContext = new BehatContext(phpClass);

        for (String name : behatContext.getSubContexts().keySet()) {
            result.addElement(new SimpleTextLookup(name));
        }
    }
}
