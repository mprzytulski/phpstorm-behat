package pl.projectspace.idea.plugins.php.behat.psi.element;

import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocTag;
import com.jetbrains.php.lang.psi.elements.Method;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;
import org.jetbrains.plugins.cucumber.steps.AbstractStepDefinition;
import pl.projectspace.idea.plugins.php.behat.code.annotation.BehatAnnotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatStepImplementation {

    private final static Pattern implementationRegexp = Pattern.compile("(?s).*@(Given|When|Then).*");

    private final Method method;

    private GherkinStep step;

    public BehatStepImplementation(Method method, GherkinStep gherkinStep) {
        this.method = method;
        this.step = gherkinStep;
    }

    public BehatStepImplementation(Method method) {
        this.method = method;
        this.step = null;
    }


    public boolean isImplementationOf(GherkinStep step) {
        for (PhpDocTag tag : getStepsFrom(method.getDocComment())) {
            String pattern = tag.getTagValue().replaceAll("(\\?P<[^>]+>)", "");
            pattern = pattern.substring(1, pattern.length()-1);
            if (step.getSubstitutedName().matches(pattern)) {
                return true;
            }
        }

        return false;
    }

    public GherkinStep getDefinition() {
        return step;
    }

    public void setDefinition(GherkinStep step) {
        this.step = step;
    }

    public Method getMethod() {
        return method;
    }

    public static boolean isStepImplementation(Method method) {
        PhpDocComment comment = method.getDocComment();
        if (comment == null) {
            return false;
        }

        return implementationRegexp.matcher(comment.getText()).matches();
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
