package pl.projectspace.idea.plugins.php.behat.behat.step;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocTag;
import com.jetbrains.php.lang.psi.elements.Method;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;
import pl.projectspace.idea.plugins.php.behat.code.annotation.BehatAnnotation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatStep {

    private final static Pattern implementationRegexp = Pattern.compile("(?s).*@(Given|When|Then).*");

    private final Method method;

    private final PhpDocComment comment;

    private GherkinStep step;

    public BehatStep(Method method, GherkinStep gherkinStep) {
        this.method = method;
        this.step = gherkinStep;
        this.comment = null;
    }

    public BehatStep(PhpDocComment comment, GherkinStep gherkinStep) {
        this.step = gherkinStep;
        PsiElement method = comment.getNextSibling();
        do {
            if (method instanceof Method) {
                break;
            }
        }
        while ((method = method.getNextSibling()) != null);

        this.method = (Method)method;
        this.comment = comment;
    }

    public BehatStep(Method method) {
        this.method = method;
        this.step = null;
        this.comment = null;
    }


    public static boolean isImplementationOf(PhpDocComment comment, GherkinStep step) {
        for (PhpDocTag tag : getStepsFrom(comment)) {
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

    private static List<PhpDocTag> getStepsFrom(PhpDocComment comment) {
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
