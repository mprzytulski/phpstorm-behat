package pl.projectspace.idea.plugins.php.behat.code.completion.provider;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.ClassReference;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.service.ContextLocator;
import pl.projectspace.idea.plugins.php.behat.service.PageObjectLocator;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageNameProvider extends CompletionProvider<CompletionParameters>
{

    public class AnnotationLookup extends LookupElement
    {
        private String annotation;

        public AnnotationLookup(String annotation){
            this.annotation = annotation;
        }

        @NotNull
        @Override
        public String getLookupString() {
            return annotation;
        }
    }

    @Override
    protected void addCompletions(@NotNull CompletionParameters parameters, ProcessingContext context, @NotNull CompletionResultSet result) {
        PsiElement position = parameters.getPosition();

        MethodReference method = (MethodReference)PsiTreeUtil.getParentOfType(position, new Class[]{MethodReference.class});
        PhpClass phpClass = (PhpClass)PsiTreeUtil.getParentOfType(position, new Class[]{PhpClass.class});
        if (phpClass == null || method == null) {
            return;
        }

        if (!ServiceManager.getService(phpClass.getProject(), ContextLocator.class).isContextClass(phpClass)) {
            return;
        }


        if (!method.getName().equalsIgnoreCase("getPage")) {
            return;
        }

//        PsiElement e = method.getPrevSibling();
//        PsiElement e = method.getParent();
//        PsiElement e1 = e.getPrevSibling();
//        PsiElement e2 = e.getParent();

        Collection<PhpClass> pages = ServiceManager.getService(phpClass.getProject(), PageObjectLocator.class).getPages();

        for (PhpClass page : pages) {
            result.addElement(new AnnotationLookup(page.getName()));
        }
    }
}
