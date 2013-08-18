package pl.projectspace.idea.plugins.php.behat.service.validator;

import com.jetbrains.php.lang.psi.elements.MethodReference;
import pl.projectspace.idea.plugins.php.behat.service.exceptions.InvalidMethodArgumentsException;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public interface MethodCallValidator {
    public boolean isValidCall(MethodReference methodReference) throws InvalidMethodArgumentsException;
}
