package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page;

import com.jetbrains.php.lang.psi.elements.MethodReference;
import pl.projectspace.idea.plugins.commons.php.code.type.GenericMethodCallTypeProvider;
import pl.projectspace.idea.plugins.commons.php.psi.element.MethodDecorator;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.core.annotations.BehatTypeProvider;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class GetPageMethodTypeProvider extends GenericMethodCallTypeProvider {
    @Override
    @BehatTypeProvider
    protected MethodDecorator getMethod(MethodReference method) throws InvalidArgumentException, MissingElementException {
        return new GetPageMethodDecorator(method);
    }
}
