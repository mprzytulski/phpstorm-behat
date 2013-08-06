package pl.projectspace.idea.plugins.php.behat.code.completion.provider;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFileSystemItem;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.cucumber.psi.GherkinFeature;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class FeatureAnnotationProvider extends CompletionProvider<CompletionParameters>
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

        PsiElement coveringElement = PsiTreeUtil.getParentOfType(position, new Class[]{GherkinStep.class, GherkinFeature.class, PsiFileSystemItem.class});
        if (!(coveringElement instanceof GherkinFeature)) {
            return;
        }

        // agile annotations
        result.addElement(new AnnotationLookup("sprint:"));

        // mink drivers
        result.addElement(new AnnotationLookup("javascript"));
        result.addElement(new AnnotationLookup("mink:goutte"));
        result.addElement(new AnnotationLookup("mink:zombie"));
        result.addElement(new AnnotationLookup("mink:sahi"));
        result.addElement(new AnnotationLookup("mink:selenium2"));

        // execution
        result.addElement(new AnnotationLookup("postponed"));
        result.addElement(new AnnotationLookup("slow"));

        // generic
        result.addElement(new AnnotationLookup("wip"));
        result.addElement(new AnnotationLookup("missing-edge-case"));
    }
}
