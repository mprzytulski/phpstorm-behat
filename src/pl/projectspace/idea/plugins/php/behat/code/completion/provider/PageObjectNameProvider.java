package pl.projectspace.idea.plugins.php.behat.code.completion.provider;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.psi.element.context.PageObjectContext;
import pl.projectspace.idea.plugins.php.behat.psi.element.page.PageObject;
import pl.projectspace.idea.plugins.php.behat.psi.element.lookup.SimpleTextLookup;
import pl.projectspace.idea.plugins.php.behat.psi.utils.PsiUtils;
import pl.projectspace.idea.plugins.php.behat.service.locator.PageObjectLocator;

import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectNameProvider extends CompletionProvider<CompletionParameters>
{
    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
        PsiElement position = parameters.getPosition();

        MethodReference reference = (MethodReference)PsiTreeUtil.getParentOfType(position, MethodReference.class);
        PhpClass phpClass = PsiUtils.getClass(reference);

        if (!PageObjectContext.isReferenceCall(phpClass, reference)) {
            return;
        }

        Map<String, PageObject> pages = ServiceManager.getService(phpClass.getProject(), PageObjectLocator.class).getAll();

        for (String name : pages.keySet()) {
            result.addElement(new SimpleTextLookup(name));
        }
    }
}
