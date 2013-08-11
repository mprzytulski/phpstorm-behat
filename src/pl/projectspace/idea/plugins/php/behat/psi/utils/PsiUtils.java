package pl.projectspace.idea.plugins.php.behat.psi.utils;

import com.intellij.openapi.project.Project;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.*;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PsiUtils {

    public static PhpClass getContextClass(MethodReference method) {
        return PsiTreeUtil.getParentOfType(method, PhpClass.class);
    }

    public static PhpClass getContextClass(Method method) {
        return PsiTreeUtil.getParentOfType(method, PhpClass.class);
    }

    /**
     * Return first PhpClass instance from index based on FQN of this class
     *
     * @param project
     * @param fqn
     * @return
     */
    public static PhpClass getClassByFQN(final Project project, final String fqn, String fromLocation) {
        if (fromLocation == null) {
            fromLocation = project.getBasePath();
        }

        Collection<PhpClass> result = PhpIndex.getInstance(project).getClassesByFQN(fqn);
        if (!result.isEmpty()) {
            PhpClass phpClass = null;
            while ((phpClass = result.iterator().next()) != null) {
                if (phpClass.getContainingFile().getVirtualFile().getCanonicalPath().startsWith(fromLocation)) {
                    return phpClass;
                }
            }
        }

        return null;
    }

    public static PhpClass getClassByFQN(final Project project, final String fqn) {
        return getClassByFQN(project, fqn, null);
    }

    /**
     * Return PhpClass instance based on given MethodReference expression.
     *
     * This method will resolve class reference to find type of response and will return PhpClass based on class name
     * stored in type
     *
     * @param expression
     * @return
     */
    public static PhpClass getClass(MethodReference expression, String fromLocation) {
//        || !expression.getClassReference().getType().equals(PhpType.OBJECT)
        // @todo - better check if given expression belongs to proper calls
        if (expression == null || expression.getClassReference() == null) {
            return null;
        }

        Pattern pattern = Pattern.compile("^([^\\\\]*)([\\\\a-zA-Z_0-9]*)(.*)$");
        Matcher matcher = pattern.matcher(expression.getClassReference().getType().toString());

        if (matcher.matches() && matcher.groupCount() == 3) {
            return PsiUtils.getClassByFQN(expression.getProject(), matcher.group(2), fromLocation);
        }

        return null;
    }

    public static PhpClass getClass(MethodReference expression) {
        return getClass(expression, null);
    }

}
