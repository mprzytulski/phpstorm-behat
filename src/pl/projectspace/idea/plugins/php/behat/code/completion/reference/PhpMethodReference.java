package pl.projectspace.idea.plugins.php.behat.code.completion.reference;

import com.intellij.codeInsight.completion.impl.CamelHumpMatcher;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.*;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.completion.PhpClassLookupElement;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PhpMethodReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {

    private String methodFQN;
    private boolean provideVariants = false;

    public PhpMethodReference(@NotNull PsiElement element, String methodFQN) {
        super(element);
        this.methodFQN = methodFQN;
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {

        System.out.println(methodFQN);

        PhpIndex phpIndex = PhpIndex.getInstance(getElement().getProject());
        Collection<PhpClass> phpClasses = phpIndex.getClassesByFQN(methodFQN);

        List<ResolveResult> results = new ArrayList<ResolveResult>();
        for (PhpClass phpClass : phpClasses) {
            results.add(new PsiElementResolveResult(phpClass));
        }

        return results.toArray(new ResolveResult[results.size()]);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        ResolveResult[] resolveResults = multiResolve(false);
        return resolveResults.length == 1 ? resolveResults[0].getElement() : null;
    }

    @NotNull
    @Override
    public Object[] getVariants() {

        List<LookupElement> results = new ArrayList<LookupElement>();

        // not break old calls to this
        // @TODO: check performance and migrate all custom completion to this method
        if(!this.provideVariants) {
            return results.toArray();
        }

        PhpIndex phpIndex = PhpIndex.getInstance(this.getElement().getProject());
//        for (String name : phpIndex.getAllClassNames(new CamelHumpMatcher(this.methodFQN))) {
//            Collection<PhpClass> classes = phpIndex.getClassesByName(name);
//            for(PhpClass phpClass: classes) {
//                results.add(new PhpClassLookupElement(phpClass, true, PhpClassReferenceInsertHandler.getInstance()));
//            }
//        }

        return results.toArray();
    }
}
