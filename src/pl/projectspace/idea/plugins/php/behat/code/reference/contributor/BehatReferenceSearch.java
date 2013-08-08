package pl.projectspace.idea.plugins.php.behat.code.reference.contributor;

import com.intellij.psi.PsiReference;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.Processor;
import com.intellij.util.QueryExecutor;
import org.jetbrains.annotations.NotNull;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatReferenceSearch implements QueryExecutor<PsiReference, ReferencesSearch.SearchParameters> {
    @Override
    public boolean execute(@NotNull ReferencesSearch.SearchParameters queryParameters, @NotNull Processor<PsiReference> consumer) {
//        System.out.println("Search");
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
