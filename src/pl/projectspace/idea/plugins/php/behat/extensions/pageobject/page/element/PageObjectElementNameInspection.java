package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.element;

import com.intellij.codeInspection.ProblemsHolder;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import pl.projectspace.idea.plugins.commons.php.psi.element.MethodDecorator;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.commons.php.utils.PhpStringUtils;
import pl.projectspace.idea.plugins.php.behat.core.BehatMethodParameterInspection;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.GetPageMethodDecorator;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.action.GeneratePageObjectElementFix;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.action.GeneratePageObjectFix;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectElementNameInspection extends BehatMethodParameterInspection {

    @Override
    protected MethodDecorator createDecoratedMethod(MethodReference reference) throws MissingElementException, InvalidArgumentException {
        return new GetElementMethodDecorator(reference);
    }

    @Override
    protected void registerProblem(ProblemsHolder holder, MethodDecorator element) {
        if (!element.hasParameter(0)) {
            return;
        }
        holder.registerProblem(
            element.getParameter(0),
            "Can not locate named element",
            new GeneratePageObjectElementFix(
                PhpStringUtils.normaliseToClassName(((StringLiteralExpression) element.getParameter(0)).getContents())
            )
        );
    }

}
