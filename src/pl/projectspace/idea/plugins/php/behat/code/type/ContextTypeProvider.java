package pl.projectspace.idea.plugins.php.behat.code.type;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.DumbService;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.*;
import org.jetbrains.annotations.Nullable;
import pl.projectspace.idea.plugins.php.behat.psi.element.context.BehatContext;
import pl.projectspace.idea.plugins.php.behat.psi.utils.PsiUtils;
import pl.projectspace.idea.plugins.php.behat.service.locator.ContextLocator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ContextTypeProvider extends AbstractClassTypeProvider {

    /**
     * Return FQN of type for context retrieve methods of BehatContext classes
     *
     * @param psiElement
     * @return
     */
    @Nullable
    @Override
    public String getType(PsiElement psiElement) {
        if (DumbService.isDumb(psiElement.getProject())) {
            return null;
        }

        if (!(psiElement instanceof MethodReference)) {
            return null;
        }

        MethodReference method = (MethodReference)psiElement;
        PhpClass callClass = PsiUtils.getClass(method);

        if(!BehatContext.is(callClass)) {
            return null;
        }

        String type = null;

        if (method.getName().equalsIgnoreCase("getMainContext")) {
            type = getMainContextType(method);
        } else if (method.getName().equalsIgnoreCase("getSubContext")) {
            type = getSubContextType(method);
        } else if (method.getName().equalsIgnoreCase("getSubcontextByClassName")) {
            type = getSubContextTypeByClassName(method);
        }

        return type;
    }

    /**
     * Return main context type for getMainContext() call
     *
     * @param method
     * @return
     */
    private String getMainContextType(MethodReference method) {
        BehatContext context = null;

        if ((context = ServiceManager.getService(method.getProject(), ContextLocator.class).getMainContext()) == null) {
            return null;
        }

        return context.getDecoratedObject().getFQN();
    }

    /**
     * Return SubContext type for getSubContext('name_of_sub_context') call
     *
     * @param method
     * @return
     */
    private String getSubContextType(MethodReference method) {
        if (method.getParameters().length != 1) {
            return null;
        }

        PhpClass parentContext = PsiUtils.getClass(method);
        PsiElement[] parameters = method.getParameters();

        if (parentContext == null || !(parameters[0] instanceof StringLiteralExpression)) {
            return null;
        }

        StringLiteralExpression name = (StringLiteralExpression) parameters[0];
        BehatContext behatContext = new BehatContext(parentContext);

        BehatContext subContext = null;
        if ((subContext = behatContext.getSubContext(name.getContents())) == null) {
            return null;
        }

        return subContext.getDecoratedObject().getFQN();
    }

    /**
     * Return SubContext type for getSubContext('\Full\Name\Of\Context\Class') call
     *
     * @param method
     * @return
     */
    private String getSubContextTypeByClassName(MethodReference method) {
        if (method.getParameters().length == 0) {
            return null;
        }
        StringLiteralExpression name = (StringLiteralExpression) method.getParameters()[0];

        PhpClass context = PsiUtils.getClassByFQN(method.getProject(), name.getContents());

        if (context == null) {
            return null;
        }

        return context.getFQN();
    }

}
