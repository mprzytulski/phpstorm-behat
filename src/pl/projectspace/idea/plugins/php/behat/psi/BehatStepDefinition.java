package pl.projectspace.idea.plugins.php.behat.psi;

import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.CachedValueProvider;
import com.intellij.psi.util.CachedValuesManager;
import com.jetbrains.php.lang.psi.elements.Method;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;
import org.jetbrains.plugins.cucumber.steps.AbstractStepDefinition;

import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatStepDefinition extends AbstractStepDefinition {

    public BehatStepDefinition(@NotNull PsiElement element) {
        super(element);
    }

    @Override
    public List<String> getVariableNames() {
        return (List<String>)((GherkinStep)getElement()).getSubstitutedNameList();
    }

    @Override
    public String getElementText() {
        return ((GherkinStep)getElement()).getText();
    }


    public static BehatStepDefinition getStepDefinition(final Method statement) {
        return CachedValuesManager.getManager(statement.getProject()).getCachedValue(statement, new CachedValueProvider<BehatStepDefinition>() {
            @Nullable
            @Override
            public Result<BehatStepDefinition> compute() {
                final Document document = PsiDocumentManager.getInstance(statement.getProject()).getDocument(statement.getContainingFile());
                return Result.create(new BehatStepDefinition(statement), document);
            }
        });
    }

    public boolean isImplementationOf(GherkinStep step) {
        return true;
//        String clean = "^(" + step.getKeyword().getText() + "\\s*)";
//        String stepText = step.getText().replaceAll(clean, "").trim();
//        String matcher = getElementText().replaceAll("^(\\/\\^)", "").replaceAll("(\\$\\/)$", "");
//
//        return stepText.matches(matcher);
    }
}
