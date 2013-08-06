package pl.projectspace.idea.plugins.php.behat.code.completion.reference.provider;

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementResolveResult;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocTag;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.impl.MethodReferenceImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;
import pl.projectspace.idea.plugins.php.behat.code.completion.reference.PhpClassReference;
import pl.projectspace.idea.plugins.php.behat.code.completion.reference.PhpMethodReference;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatStepScenarioReferenceProvider extends PsiReferenceProvider {
    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        if (!(element instanceof GherkinStep)) {
            return new PsiReference[0];
        }

        GherkinStep step = (GherkinStep) element;
        PhpIndex index = PhpIndex.getInstance(element.getProject());

//        Collection<PhpClass> contextList = index.getAllSubclasses("\\Behat\\Behat\\Context\\BehatContext");
        Collection<PhpClass> contextList = index.getClassesByFQN("\\TestContext");

        ArrayList<PsiReference> references = new ArrayList<PsiReference>();

        for (PhpClass phpClass : contextList) {
//            System.out.println(phpClass.getContainingFile().getVirtualFile().getCanonicalPath());
//            if (phpClass.getContainingFile().getVirtualFile().getPath().startsWith("vendors"))

            Collection<Method> methods = phpClass.getMethods();
            for (Method method : methods) {
                if (!method.getAccess().isPublic()) {
                    continue;
                }

                PhpDocComment comment = method.getDocComment();
                PhpDocTag[] tags = comment.getTagElementsByName("@" + step.getKeyword().getText());
                if (tags.length > 0) {
                    String clean = "^(" + step.getKeyword().getText() + "\\s*)";
                    String stepText = element.getText().replaceAll(clean, "").trim();

                    for (PhpDocTag tag : tags) {
                        String matcher = tags[0].getTagValue().replaceAll("^(\\/\\^)", "").replaceAll("(\\$\\/)$", "");
                        if (stepText.matches(matcher)) {
                            System.out.println(method.getFQN());

//                            PhpClassReference r = new PhpClassReference(element, phpClass.getFQN());
//
//                            references.add(r);
                        }
                    }
                }
            }
        }

        return references.toArray(new PsiReference[references.size()]);
    }
}
