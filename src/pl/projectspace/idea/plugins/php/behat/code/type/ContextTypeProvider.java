package pl.projectspace.idea.plugins.php.behat.code.type;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.ParameterList;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.PhpNamedElement;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import com.jetbrains.php.lang.psi.resolve.types.PhpTypeProvider2;
import org.jetbrains.annotations.Nullable;
import pl.projectspace.idea.plugins.php.behat.service.ContextLocator;
import pl.projectspace.idea.plugins.php.behat.service.PageObjectLocator;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class ContextTypeProvider implements PhpTypeProvider2 {

    final static char TRIM_KEY = '\u0180';

    @Override
    public char getKey() {
        return TRIM_KEY;
    }

    @Nullable
    @Override
    public String getType(PsiElement psiElement) {
        if (DumbService.isDumb(psiElement.getProject())) {
            return null;
        }

        if (psiElement instanceof MethodReference) {
            MethodReference method = (MethodReference)psiElement;

            String type = null;

            if (method.getName().equals("getMainContext")) {
                type = getMainContextType(method);
            }
            else if (method.getName().equals("getSubContext")) {
                type = getSubContextType(method);
            }

            return type;

//            PhpClass phpClass = PsiTreeUtil.getContextOfType(psiElement, PhpClass.class, false);
//
//            PageObjectLocator locator = ServiceManager.getService(psiElement.getProject(), PageObjectLocator.class);
//
//            if (!locator.isPageObjectContext(phpClass)) {
//                return null;
//            }
//
//            ParameterList parameters = method.getParameterList();
//            if (parameters == null || parameters.getFirstPsiChild() == null) {
//                return null;
//            }
//
//            PsiElement[] params = parameters.getParameters();
//            StringLiteralExpressionImpl param = (StringLiteralExpressionImpl) params[0];
//
//            PhpClass returnType = locator.getPageObjectClass(psiElement.getProject(), param.getContents());
//
//            if (returnType == null) {
//                return null;
//            }
//
//            return returnType.getFQN();
        }

        return null;
    }

    @Override
    public Collection<? extends PhpNamedElement> getBySignature(String expression, Project project) {
        PhpIndex phpIndex = PhpIndex.getInstance(project);
        PhpClass phpClass = phpIndex.getClassesByFQN(expression).iterator().next();

        return Arrays.asList(phpClass);
    }

    private String getMainContextType(MethodReference method) {
        PhpClass methodOwner = getClassForMethod(method);

        ContextLocator locator = ServiceManager
            .getService(method.getProject(), ContextLocator.class);

        PhpClass context = locator.getMainContextFor(methodOwner);

        if (context == null) {
            return null;
        }

        return context.getFQN();
    }

    private String getSubContextType(MethodReference method) {
        return null;
    }

    private PhpClass getClassForMethod(MethodReference method) {
        return PsiTreeUtil.getParentOfType(method, PhpClass.class);
    }
}
