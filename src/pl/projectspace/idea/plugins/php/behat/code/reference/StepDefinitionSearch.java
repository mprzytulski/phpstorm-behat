package pl.projectspace.idea.plugins.php.behat.code.reference;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.Processor;
import com.intellij.util.QueryExecutor;
import com.jetbrains.php.lang.psi.elements.Method;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.BehatUtil;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class StepDefinitionSearch implements QueryExecutor<PsiReference, ReferencesSearch.SearchParameters> {

    @Override
    public boolean execute(@NotNull ReferencesSearch.SearchParameters queryParameters, @NotNull Processor<PsiReference> consumer) {
        final PsiElement myElement = queryParameters.getElementToSearch();
        if (!(myElement instanceof Method)){
            return true;
        }

        final Method method = (Method) myElement;
        if (!BehatUtil.isStepDefinition(method)) {
            return true;
        }

//        final Ref<PsiAnnotation> stepAnnotation = new Ref<PsiAnnotation>();
//        ApplicationManager.getApplication().runReadAction(new Runnable() {
//            @Override
//            public void run() {
//                stepAnnotation.set(CucumberJavaUtil.getCucumberStepAnnotation(method));
//            }
//        });
//
//        assert stepAnnotation.get() != null;
//        final String regexp = CucumberJavaUtil.getPatternFromStepDefinition(stepAnnotation.get());
//        if (regexp == null) {
//            return true;
//        }
//
//        final String word = CucumberUtil.getTheBiggestWordToSearchByIndex(regexp);
//        if (StringUtil.isEmpty(word)) {
//            return true;
//        }
//
//        final SearchScope searchScope = CucumberStepSearchUtil.restrictScopeToGherkinFiles(new Computable<SearchScope>() {
//            public SearchScope compute() {
//                return queryParameters.getEffectiveSearchScope();
//            }
//        });
//        // As far as default CacheBasedRefSearcher doesn't look for references in string we have to write out own to handle this correctly
//        final TextOccurenceProcessor processor = new TextOccurenceProcessor() {
//            @Override
//            public boolean execute(PsiElement element, int offsetInElement) {
//                PsiElement parent = element.getParent();
//                if (parent == null) return true;
//
//                for (PsiReference ref : parent.getReferences()) {
//                    if (ref != null && ref.isReferenceTo(myElement)) {
//                        if (!consumer.process(ref)) {
//                            return false;
//                        }
//                    }
//                }
//                return true;
//            }
//        };
//
//        short context = UsageSearchContext.IN_STRINGS | UsageSearchContext.IN_CODE;
//        return PsiSearchHelper.SERVICE.getInstance(myElement.getProject()).
//                processElementsWithWord(processor, searchScope, word, context, true);
        return false;
    }
}
