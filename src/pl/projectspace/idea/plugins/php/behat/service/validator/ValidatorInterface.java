package pl.projectspace.idea.plugins.php.behat.service.validator;

import com.intellij.psi.PsiElement;
import pl.projectspace.idea.plugins.php.behat.service.exceptions.InvalidMethodArgumentsException;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public interface ValidatorInterface {

    public boolean isValidCall(PsiElement element) throws InvalidMethodArgumentsException;

}
