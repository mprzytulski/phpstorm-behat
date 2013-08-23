package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page;

import com.intellij.codeInspection.*;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import pl.projectspace.idea.plugins.commons.php.code.inspection.GenericMethodParameterInspection;
import pl.projectspace.idea.plugins.commons.php.psi.element.MethodDecorator;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.core.BehatMethodParameterInspection;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.action.GeneratePageObjectFix;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectNameInspection extends BehatMethodParameterInspection {

    @Override
    protected MethodDecorator createDecoratedMethod(MethodReference reference) throws MissingElementException, InvalidArgumentException {
        return new GetPageMethodDecorator(reference);
    }

    @Override
    protected void registerProblem(ProblemsHolder holder, MethodDecorator element) {
        if (!element.hasParameter(0)) {
            return;
        }
        holder.registerProblem(element.getParameter(0), "Can not locate named page", new GeneratePageObjectFix());
    }

}
