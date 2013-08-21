package pl.projectspace.idea.plugins.php.behat.context;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.InvalidArgumentException;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.psi.element.MethodCallDecorator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class GetSubContextMethodDecorator extends MethodCallDecorator {

    protected BehatContext context;
    protected final ContextLocator contextLocator;

    public GetSubContextMethodDecorator(PsiElement element) throws InvalidArgumentException, MissingElementException {
        super(element, "getSubContext");

        contextLocator = behatProject.getService(ContextLocator.class);

        context = contextLocator.locate(getTarget().getFQN());
    }

    @Override
    public BehatContext getReturnType() throws MissingElementException {
        return (BehatContext) super.getReturnType();
    }

    @Override
    protected Object resolveType() throws MissingElementException {
        if (!hasParameter(0, StringLiteralExpression.class)) {
            throw new MissingElementException("Missing parameter");
        }

        return contextLocator.getMainContext().getSubContext(
            ((StringLiteralExpression) getParameter(0)).getContents()
        );
    }
}
