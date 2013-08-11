package pl.projectspace.idea.plugins.php.behat.psi.element.context;

import com.intellij.openapi.components.ServiceManager;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.php.behat.psi.utils.PsiUtils;
import pl.projectspace.idea.plugins.php.behat.service.locator.ContextLocator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectContext extends BehatContext {

    public PageObjectContext(PhpClass phpClass) {
        super(phpClass);
    }

    /**
     * Check if given class is instanceof PageObject Object Context
     *
     * @param phpClass
     * @return
     */
    public static boolean is(PhpClass phpClass) {
        return (
            ServiceManager.getService(phpClass.getProject(), ContextLocator.class).isPageObjectContext(phpClass)
        );
    }

    /**
     * Check if given method reference call is getPage call
     * @return
     */
    public static boolean isReferenceCall(MethodReference reference) {
        PhpClass phpClass = PsiUtils.getClass(reference);

        return isReferenceCall(phpClass, reference);
    }

    public static boolean isReferenceCall(PhpClass phpClass, MethodReference reference) {
        return (reference != null && reference.getName().equalsIgnoreCase("getPage") && is(phpClass));
    }

}
