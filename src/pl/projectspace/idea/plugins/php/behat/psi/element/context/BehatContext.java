package pl.projectspace.idea.plugins.php.behat.psi.element.context;

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
import pl.projectspace.idea.plugins.php.behat.code.annotation.BehatAnnotation;
import pl.projectspace.idea.plugins.php.behat.psi.element.PhpClassDecorator;
import pl.projectspace.idea.plugins.php.behat.psi.element.step.BehatStep;
import pl.projectspace.idea.plugins.php.behat.service.locator.BehatContextLocator;

import java.util.*;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatContext extends PhpClassDecorator {

    private BehatContextLocator locator;

    public BehatContext(PhpClass phpClass) {
        super(phpClass);

        locator = ServiceManager.getService(phpClass.getProject(), BehatContextLocator.class);
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

        BehatContext base = locator.getBaseContext();
        Method useContextMethod = base.getDecoratedObject().findMethodByName("useContext");
        Query<PsiReference> query = ReferencesSearch.search(useContextMethod, new LocalSearchScope(getDecoratedObject()));

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
    public BehatContext getSubContext(String name) {
        Map<String, BehatContext> subs = getSubContexts();

        if (!subs.keySet().contains(name)) {
            return null;
        }

        return subs.get(name);
    }

    /**
     * Return Main Context representation
     *
     * @return
     */
    public BehatContext getMainContext() {
        return locator.getMainContext();
    }

    /**
     * Check if given PhpClass instance is Behat context
     *
     * @param phpClass
     * @return
     */
    public static boolean is(PhpClass phpClass) {
        return (phpClass != null && ServiceManager.getService(phpClass.getProject(), BehatContextLocator.class).isBehatContext(phpClass));
    }

    /**
     * Check if given reference contains proper context method
     *
     * @param reference
     * @return
     */
    public static boolean isProperReferenceCallMethodName(MethodReference reference) {
        return (reference != null && reference.getName().equalsIgnoreCase("getSubContext"));
    }
}
