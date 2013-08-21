package pl.projectspace.idea.plugins.php.behat.psi.reference;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.Method;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;
import pl.projectspace.idea.plugins.commons.php.psi.reference.PhpMethodReference;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class MethodReference extends PhpMethodReference {
    public MethodReference(@NotNull Method method, @NotNull PsiElement name) {
        super(method, name);
    }

    @NotNull
    @Override
    public String getCanonicalText() {
        return ((GherkinStep)name).getText();
    }
}
