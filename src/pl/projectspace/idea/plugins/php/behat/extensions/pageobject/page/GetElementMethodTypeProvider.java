package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page;

import com.jetbrains.php.lang.psi.elements.MethodReference;
import pl.projectspace.idea.plugins.commons.php.code.type.GenericMethodCallTypeProvider;
import pl.projectspace.idea.plugins.commons.php.psi.element.MethodDecorator;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.element.GetElementMethodDecorator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class GetElementMethodTypeProvider extends GenericMethodCallTypeProvider {
    @Override
    protected MethodDecorator getMethod(MethodReference method) throws InvalidArgumentException, MissingElementException {
        return new GetElementMethodDecorator(method);
    }
}
