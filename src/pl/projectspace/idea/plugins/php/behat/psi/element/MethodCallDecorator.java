package pl.projectspace.idea.plugins.php.behat.psi.element;

import com.intellij.psi.PsiElement;
import pl.projectspace.idea.plugins.commons.php.psi.element.MethodDecorator;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public abstract class MethodCallDecorator extends BehatMethod {
    public MethodCallDecorator(PsiElement element, String expectedName) throws InvalidArgumentException {
        super(element);
        if (!this.element.getName().equalsIgnoreCase(expectedName)) {
            throw new InvalidArgumentException(
                String.format(
                    "Can not create instance of method with name %s when expected %s.",
                    this.element.getName(),
                    expectedName
                )
            );
        }
    }
}
