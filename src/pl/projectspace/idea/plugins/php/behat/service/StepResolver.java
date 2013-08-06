package pl.projectspace.idea.plugins.php.behat.service;

import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StorageScheme;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocTag;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;

import java.util.ArrayList;
import java.util.Collection;

@State(
        name = "StepResolver",
        storages = {
                @Storage(id = "default", file="$PROJECT_CONFIG_DIR$/step_resolver.xml", scheme = StorageScheme.DIRECTORY_BASED)
        }
)

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class StepResolver {

    public Method getImplementationMethodFor(GherkinStep step) {
        PhpIndex index = PhpIndex.getInstance(step.getProject());

//        Collection<PhpClass> contextList = index.getAllSubclasses("\\Behat\\Behat\\Context\\BehatContext");
        Collection<PhpClass> contextList = index.getClassesByFQN("\\TestContext");

        ArrayList<PsiReference> references = new ArrayList<PsiReference>();

        for (PhpClass phpClass : contextList) {

            Collection<Method> methods = phpClass.getMethods();
            for (Method method : methods) {
                if (!method.getAccess().isPublic()) {
                    continue;
                }

                PhpDocComment comment = method.getDocComment();
                PhpDocTag[] tags = comment.getTagElementsByName("@" + step.getKeyword().getText());
                if (tags.length > 0) {
                    String clean = "^(" + step.getKeyword().getText() + "\\s*)";
                    String stepText = step.getText().replaceAll(clean, "").trim();

                    for (PhpDocTag tag : tags) {
                        String matcher = tags[0].getTagValue().replaceAll("^(\\/\\^)", "").replaceAll("(\\$\\/)$", "");
                        if (stepText.matches(matcher)) {
                            System.out.println(method.getFQN());
                            return method;
                        }
                    }
                }
            }
        }

        return null;

    }

}
