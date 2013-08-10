package pl.projectspace.idea.plugins.php.behat.psi.element;

import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocTag;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.plugins.cucumber.psi.GherkinPsiUtil;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;
import org.jetbrains.plugins.cucumber.steps.AbstractStepDefinition;
import pl.projectspace.idea.plugins.php.behat.code.annotation.BehatAnnotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatContextClass {

    private PhpClass phpClass;

    public BehatContextClass(PhpClass phpClass) {
        this.phpClass = phpClass;
    }

    public List<BehatStepImplementation> getStepImplementations() {
        ArrayList<BehatStepImplementation> list = new ArrayList<BehatStepImplementation>();

        for (Method method : this.phpClass.getMethods()) {
            if (!BehatStepImplementation.isStepImplementation(method)) {
                continue;
            }

            for(PhpDocTag def : getStepsFrom(method.getDocComment())) {
                list.add(new BehatStepImplementation(method));
            }
        }

        return list;
    }

    public PhpClass getPhpClass() {
        return this.phpClass;
    }

    public BehatStepImplementation getStepImplementation(GherkinStep step) {
        for (BehatStepImplementation implementation : getStepImplementations()) {
            if (implementation.isImplementationOf(step)) {
                implementation.setDefinition(step);
                return implementation;
            }
        }

        return null;
    }

    private List<PhpDocTag> getStepsFrom(PhpDocComment comment) {
        ArrayList<PhpDocTag> elements = new ArrayList<PhpDocTag>();
        for (String step : BehatAnnotation.step) {
            if (comment == null) {
                continue;
            }
            PhpDocTag[] tags = comment.getTagElementsByName("@" + step);
            elements.addAll(Arrays.asList(tags));
        }

        return elements;
    }
}
