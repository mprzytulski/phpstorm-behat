package pl.projectspace.idea.plugins.php.behat.psi.utils;

import com.intellij.openapi.project.Project;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.*;

import java.util.*;
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
    public static PhpClass getClassByFQN(final Project project, final String fqn, List<String> excludedNamespaces) {
        Collection<PhpClass> result = PhpIndex.getInstance(project).getClassesByFQN(fqn);
        if (!result.isEmpty()) {
            PhpClass phpClass = null;
            while ((phpClass = result.iterator().next()) != null) {
//                for (String ns : excludedNamespaces) {
//                    if (!phpClass.getNamespaceName().startsWith(ns)) {
                        return phpClass;
//                    }
//                }
            }
        }

        return null;
    }

    /**
     * Return class by FQN
     *
     * @param project
     * @param fqn
     * @return
     */
    public static PhpClass getClassByFQN(final Project project, final String fqn) {
        return getClassByFQN(project, fqn, new LinkedList<String>());
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
    public static PhpClass getClass(MethodReference expression, List<String> excludedNamespaces) {
//        || !expression.getClassReference().getType().equals(PhpType.OBJECT)
        // @todo - better check if given expression belongs to proper calls
        if (expression == null || expression.getClassReference() == null) {
            return null;
        }

        Pattern pattern = Pattern.compile("^([^\\\\]*)([\\\\a-zA-Z_0-9]*)(.*)$");
        Matcher matcher = pattern.matcher(expression.getClassReference().getType().toString());

        if (matcher.matches() && matcher.groupCount() == 3) {
            return PsiUtils.getClassByFQN(expression.getProject(), matcher.group(2), excludedNamespaces);
        }

        return null;
    }

    /**
     * Return class for given MethodReference expression (resolving reference)
     *
     * @param expression
     * @return
     */
    public static PhpClass getClass(MethodReference expression) {
        return getClass(expression, new LinkedList<String>());
    }
}
