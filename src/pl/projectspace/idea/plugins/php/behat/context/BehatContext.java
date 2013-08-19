package pl.projectspace.idea.plugins.php.behat.context;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.LocalSearchScope;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.intellij.util.Query;
import com.jetbrains.php.lang.documentation.phpdoc.psi.PhpDocComment;
import com.jetbrains.php.lang.documentation.phpdoc.psi.tags.PhpDocTag;
import com.jetbrains.php.lang.psi.elements.*;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;
import pl.projectspace.idea.plugins.php.behat.code.BehatProjectPhpClass;
import pl.projectspace.idea.plugins.php.behat.code.annotation.BehatAnnotation;
import pl.projectspace.idea.plugins.php.behat.context.exceptions.MissingContextException;
import pl.projectspace.idea.plugins.php.behat.service.validator.BehatContextValidator;
import pl.projectspace.idea.plugins.php.behat.step.BehatStep;
import pl.projectspace.idea.plugins.php.behat.service.locator.BehatContextLocator;

import java.util.*;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatContext extends BehatProjectPhpClass {

    /**
     * Base Interface for Behat Context for All Object
     */
    public static final String BASE_CONTEXT_INTERFACE = "\\Behat\\Behat\\Context\\ContextInterface";

    /**
     * Base context class
     */
    public static final String BASE_CONTEXT_CLASS = "\\Behat\\Behat\\Context\\BehatContext";

    public static final LinkedList<String> BUILDIN_CONTEXT_NAMESPACES = new LinkedList<String>();

    static {
        BUILDIN_CONTEXT_NAMESPACES.add("\\Behat\\Behat\\Snippet\\");
    }


    public BehatContext(PhpClass phpClass) {
        super(phpClass);
    }

    public List<BehatStep> getStepImplementations() {
        ArrayList<BehatStep> list = new ArrayList<BehatStep>();

        for (Method method : this.phpClass.getMethods()) {
            if (!BehatStep.isStepImplementation(method)) {
                continue;
            }

            for(PhpDocTag def : getStepsFrom(method.getDocComment())) {
                list.add(new BehatStep(method));
            }
        }

        return list;
    }

    public BehatStep getStepImplementation(GherkinStep step) {
        for (BehatStep implementation : getStepImplementations()) {
//            if (implementation.isImplementationOf(step)) {
//                implementation.setDefinition(step);
//                return implementation;
//            }
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

    /**
     * Get named map of available sub contexts for current context
     *
     * @return
     */
    public Map<String, BehatContext> getSubContexts() {
        HashMap<String, BehatContext> subContexts = new HashMap<String, BehatContext>();
        BehatContext baseContext = (getBehatProject().getService(BehatContextLocator.class)).getMainContext();

        if (baseContext == null) {
            return Collections.emptyMap();
        }

        Method useContextMethod = baseContext.getDecoratedObject().findMethodByName("useContext");
        Query<PsiReference> query = ReferencesSearch.search(useContextMethod, new LocalSearchScope(baseContext.getDecoratedObject()));

        for (PsiReference use : query.findAll()) {
            MethodReference method = (MethodReference) use;
            PsiElement[] parameters = method.getParameters();

            if (parameters.length != 2) {
                continue;
            }

            String name = ((StringLiteralExpression) parameters[0]).getContents();
            PhpClass type = (PhpClass) (((NewExpression) parameters[1]).getClassReference().resolve());
            if (type == null) {
                continue;
            }
            subContexts.put(name, ContextFactory.create(type));
        }

        return subContexts;
    }

    /**
     * Return sub context by given name
     *
     * @param name
     * @return
     */
    public BehatContext getSubContext(String name) throws MissingContextException {
        Map<String, BehatContext> subs = getSubContexts();

        if (!subs.keySet().contains(name)) {
            throw new MissingContextException("Missing named context: " + name);
        }

        return subs.get(name);
    }

    /**
     * Return Main Context representation
     *
     * @return
     */
    public BehatContext getMainContext() {
        return (getBehatProject().getService(BehatContextLocator.class)).getMainContext();
    }

}
