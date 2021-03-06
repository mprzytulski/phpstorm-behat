package pl.projectspace.idea.plugins.php.behat.context;

import com.jetbrains.php.lang.psi.elements.MethodReference;
import pl.projectspace.idea.plugins.commons.php.code.type.GenericMethodCallTypeProvider;
import pl.projectspace.idea.plugins.commons.php.psi.element.MethodDecorator;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.commons.php.utils.annotation.DependsOnPlugin;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
@DependsOnPlugin("behat")
public class GetSubContextTypeProvider extends GenericMethodCallTypeProvider {
    @Override
    protected MethodDecorator getMethod(MethodReference method) throws InvalidArgumentException, MissingElementException {
        return new GetSubContextMethodDecorator(method);
    }
}
