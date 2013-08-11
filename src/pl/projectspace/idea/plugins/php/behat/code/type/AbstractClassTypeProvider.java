package pl.projectspace.idea.plugins.php.behat.code.type;

import com.intellij.openapi.project.Project;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.PhpNamedElement;
import com.jetbrains.php.lang.psi.resolve.types.PhpTypeProvider2;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
abstract public class AbstractClassTypeProvider implements PhpTypeProvider2 {

    final static char TRIM_KEY = '\u0180';

    @Override
    public char getKey() {
        return TRIM_KEY;
    }

    /**
     * Return context instance for given expression - FQN
     *
     * @param expression
     * @param project
     * @return
     */
    @Override
    public Collection<? extends PhpNamedElement> getBySignature(String expression, Project project) {
        PhpIndex phpIndex = PhpIndex.getInstance(project);
        PhpClass phpClass = phpIndex.getClassesByFQN(expression).iterator().next();

        return Arrays.asList(phpClass);
    }
}
