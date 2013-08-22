package pl.projectspace.idea.plugins.php.behat.utils;

import com.intellij.openapi.project.Project;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import pl.projectspace.idea.plugins.php.behat.context.ContextLocator;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.locator.PageElementLocator;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.locator.PageObjectLocator;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatUtils {
    private final Project project;
    private final ContextLocator contextLocator;
    private final PageObjectLocator pageObjectLocator;
    private final PageElementLocator pageElementLocator;

    public BehatUtils(Project project, ContextLocator contextLocator, PageObjectLocator pageObjectLocator, PageElementLocator pageElementLocator) {
        this.project = project;
        this.contextLocator = contextLocator;
        this.pageObjectLocator = pageObjectLocator;
        this.pageElementLocator = pageElementLocator;
    }

    public boolean isBehatClass(PhpClass phpClass) {
        return (
            contextLocator.is(phpClass)
            || pageObjectLocator.is(phpClass)
            || pageElementLocator.is(phpClass)
        );
    }
}
