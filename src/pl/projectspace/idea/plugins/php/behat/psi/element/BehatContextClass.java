package pl.projectspace.idea.plugins.php.behat.psi.element;

import com.intellij.lang.ASTNode;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocTag;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.impl.PhpClassImpl;
import com.jetbrains.php.lang.psi.stubs.PhpClassStub;
import pl.projectspace.idea.plugins.php.behat.code.annotation.BehatAnnotation;
import pl.projectspace.idea.plugins.php.behat.code.generator.BehatStepCreator;
import pl.projectspace.idea.plugins.php.behat.psi.BehatStepDefinition;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatContextClass extends PhpClassImpl {
    public BehatContextClass(ASTNode node) {
        super(node);
    }

    public BehatContextClass(PhpClassStub stub) {
        super(stub);
    }

    public List<BehatStepDefinition> getStepDefinitions() {
        ArrayList<BehatStepDefinition> list = new ArrayList<BehatStepDefinition>();

        for (Method method : getMethods()) {
            PhpDocComment comment = null;
            for(String def : getStepsFrom(method.getDocComment())) {
//                step = new BehatStepDefinition();
            }
        }

        return list;
    }

    private List<String> getStepsFrom(PhpDocComment comment) {
        ArrayList<String> regexp = new ArrayList<String>();
        for (String step : BehatAnnotation.step) {
            PhpDocTag[] tags = comment.getTagElementsByName(step);
            for (PhpDocTag tag : tags) {
                regexp.add(tag.getTagValue());
            }
        }

        return regexp;
    }
}
