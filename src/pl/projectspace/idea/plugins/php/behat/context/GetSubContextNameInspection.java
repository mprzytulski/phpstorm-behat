package pl.projectspace.idea.plugins.php.behat.context;

import com.intellij.codeInspection.ProblemsHolder;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import pl.projectspace.idea.plugins.commons.php.psi.element.MethodDecorator;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.commons.php.utils.annotation.DependsOnPlugin;
import pl.projectspace.idea.plugins.commons.php.utils.annotation.RequireMethod;
import pl.projectspace.idea.plugins.php.behat.core.BehatMethodParameterInspection;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
@DependsOnPlugin("behat")
@RequireMethod("getSubContext")
public class GetSubContextNameInspection extends BehatMethodParameterInspection {

    @Override
    protected MethodDecorator createDecoratedMethod(MethodReference reference) throws MissingElementException, InvalidArgumentException {
        return new GetSubContextMethodDecorator(reference);
    }

    @Override
    protected void registerProblem(ProblemsHolder holder, MethodDecorator element) {
        if (!element.hasParameter(0)) {
            return;
        }
        holder.registerProblem(element.getParameter(0), "Can not locate subcontext");
    }

}

