package pl.projectspace.idea.plugins.php.behat.psi.element;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocTag;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.impl.PhpClassImpl;
import com.jetbrains.php.lang.psi.stubs.PhpClassStub;
import org.apache.commons.lang.ArrayUtils;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;
import pl.projectspace.idea.plugins.php.behat.code.annotation.BehatAnnotation;
import pl.projectspace.idea.plugins.php.behat.code.generator.BehatStepCreator;
import pl.projectspace.idea.plugins.php.behat.psi.BehatStepDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatContextClass {

    private PhpClass phpClass;

    public BehatContextClass(PhpClass phpClass) {

        this.phpClass = phpClass;
    }

    public List<BehatStepDefinition> getStepDefinitions() {
        ArrayList<BehatStepDefinition> list = new ArrayList<BehatStepDefinition>();

        for (Method method : this.phpClass.getMethods()) {
            for(PsiElement def : getStepsFrom(method.getDocComment())) {
                System.out.println(def.getText());
                list.add(new BehatStepDefinition(def));
            }
        }

        return list;
    }

    private List<PsiElement> getStepsFrom(PhpDocComment comment) {
        ArrayList<PsiElement> elements = new ArrayList<PsiElement>();
        for (String step : BehatAnnotation.step) {
            if (comment == null) {
                continue;
            }
            PhpDocTag[] tags = comment.getTagElementsByName("@" + step);
            elements.addAll(Arrays.asList(tags));
        }

        return elements;
    }

    public PhpClass getPhpClass() {
        return this.phpClass;
    }

    public BehatStepDefinition getStepDefinitionFor(GherkinStep step) {
        for (BehatStepDefinition definition : getStepDefinitions()) {
            if (definition.isImplementationOf(step)) {
                return definition;
            }
        }

        return null;
    }
}
