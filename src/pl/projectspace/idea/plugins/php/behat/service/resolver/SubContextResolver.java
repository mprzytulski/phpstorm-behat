package pl.projectspace.idea.plugins.php.behat.service.resolver;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.context.BehatContext;
import pl.projectspace.idea.plugins.php.behat.context.exceptions.InvalidReferenceMethodCall;
import pl.projectspace.idea.plugins.php.behat.context.exceptions.MissingContextException;
import pl.projectspace.idea.plugins.php.behat.psi.reference.BehatContextReference;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class SubContextResolver {
    public BehatContextReference resolve(MethodReference methodReference) throws InvalidReferenceMethodCall {
        try {
            BehatProject behatProject = methodReference.getProject().getComponent(BehatProject.class);

            PhpClass phpClass = behatProject.getService(PsiTreeUtils.class).getClass(methodReference);

            PsiElement[] params = methodReference.getParameters();

            StringLiteralExpressionImpl name = ((StringLiteralExpressionImpl) params[0]);

            BehatContext context = new BehatContext(phpClass);

            return new BehatContextReference(context.getSubContext(name.getContents()), name);
        } catch (MissingContextException e) {
            throw new InvalidReferenceMethodCall(e.getMessage());
        }
    }
}
