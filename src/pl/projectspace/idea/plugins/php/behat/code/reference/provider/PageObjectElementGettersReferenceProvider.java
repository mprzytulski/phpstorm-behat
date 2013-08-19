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
import pl.projectspace.idea.plugins.commons.php.service.locator.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.context.exceptions.InvalidReferenceMethodCall;
import pl.projectspace.idea.plugins.php.behat.page.PageObject;
import pl.projectspace.idea.plugins.commons.php.psi.reference.ArrayElementReference;
import pl.projectspace.idea.plugins.php.behat.service.exceptions.InvalidMethodArgumentsException;
import pl.projectspace.idea.plugins.php.behat.service.exceptions.InvalidMethodNameResolveException;
import pl.projectspace.idea.plugins.php.behat.service.locator.PageObjectLocator;
import pl.projectspace.idea.plugins.php.behat.service.resolver.PageObjectResolver;

import java.util.Map;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectElementGettersReferenceProvider extends PsiReferenceProvider {

    @NotNull
    @Override
    public PsiReference[] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext processingContext) {
        try {
            PhpClass phpClass = PsiTreeUtil.getParentOfType(element, PhpClass.class);

            BehatProject project = element.getProject().getComponent(BehatProject.class);

            PageObjectLocator locator = project.getService(PageObjectLocator.class);
            PageObject page = locator.locate(phpClass.getName());

            StringLiteralExpressionImpl name = ((StringLiteralExpressionImpl) element);

            Map<String, PsiElement> getters = page.getElementLocators();

            if (!getters.containsKey(name.getContents())) {
                return new PsiReference[0];
            }

            return new PsiReference[] { new ArrayElementReference(getters.get(name.getContents()), name) };
        } catch (MissingElementException e) {
            return new PsiReference[0];
        }
    }

}
