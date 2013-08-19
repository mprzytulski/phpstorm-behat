package pl.projectspace.idea.plugins.php.behat.code.reference.provider;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.lang.psi.elements.MethodReference;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.commons.php.psi.PsiTreeUtils;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.FailedToLocateContainingClassException;
import pl.projectspace.idea.plugins.commons.php.service.locator.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.context.PageObjectContext;
import pl.projectspace.idea.plugins.php.behat.context.exceptions.InvalidReferenceMethodCall;
import pl.projectspace.idea.plugins.php.behat.page.PageObject;
import pl.projectspace.idea.plugins.php.behat.psi.reference.PageObjectReference;
import pl.projectspace.idea.plugins.php.behat.service.exceptions.InvalidMethodArgumentsException;
import pl.projectspace.idea.plugins.php.behat.service.exceptions.InvalidMethodNameResolveException;
import pl.projectspace.idea.plugins.php.behat.service.locator.PageObjectLocator;
import pl.projectspace.idea.plugins.php.behat.service.resolver.PageObjectResolver;
import pl.projectspace.idea.plugins.php.behat.service.validator.PageObjectContextValidator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext processingContext) {
        MethodReference methodReference = PsiTreeUtil.getParentOfType(element, MethodReference.class);
        try {
            BehatProject behatProject = element.getProject().getComponent(BehatProject.class);

            PageObjectResolver resolver = behatProject.getService(PageObjectResolver.class);
            PageObjectContextValidator validator = element.getProject().getComponent(BehatProject.class).getService(PageObjectContextValidator.class);

            if (!validator.isValidCall(methodReference)) {
                return new PsiReference[0];
            }

            PhpClass phpClass = behatProject.getService(PsiTreeUtils.class).getClass(element);
            if (!validator.isPageObjectContext(phpClass)) {
                return new PsiReference[0];
            }

            return new PsiReference[] { resolver.resolve(element) };
        } catch (Exception e) {
            return new PsiReference[0];
        }
    }

}
