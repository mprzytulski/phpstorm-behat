package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.element;

import com.intellij.psi.PsiElement;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.commons.php.psi.element.PhpClassDecorator;
import pl.projectspace.idea.plugins.commons.php.psi.element.PsiElementDecorator;
import pl.projectspace.idea.plugins.php.behat.core.BehatProjectPhpClass;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageElement extends PsiElementDecorator {

    public PageElement(PhpClass phpClass) {
        super(phpClass);
    }

    public PageElement(PsiElement element) {
        super(element);
    }

    @Override
    public String toString() {
        if (element instanceof PhpClass) {
            return ((PhpClass) element).getFQN();
        }

        return null;
    }

}
