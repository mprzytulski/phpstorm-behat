package pl.projectspace.idea.plugins.php.behat.psi.element;

import com.intellij.psi.PsiElement;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;

import java.util.Arrays;
import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class MethodCallDecorator extends BehatMethod {
    public MethodCallDecorator(PsiElement element, String expectedName) throws InvalidArgumentException {
        this(element, Arrays.asList(new String[]{ expectedName }));
    }

    public MethodCallDecorator(PsiElement element, List<String> expectedNames) throws InvalidArgumentException {
        super(element);

        for (String method : expectedNames) {
            if (this.element.getName().equalsIgnoreCase(method)) {
                return;
            }
        }

        throw new InvalidArgumentException(
            String.format(
                "Can not create instance of method with name %s.",
                this.element.getName()
            )
        );
    }
}
