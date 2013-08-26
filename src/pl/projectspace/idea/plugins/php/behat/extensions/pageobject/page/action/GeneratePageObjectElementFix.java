package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.page.action;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.jetbrains.annotations.NotNull;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.action.CreatePageObjectElementFile;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.action.CreatePageObjectFile;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class GeneratePageObjectElementFix implements LocalQuickFix {

    private String pageObjectElementName;

    public GeneratePageObjectElementFix(String pageObjectElementName) {
        this.pageObjectElementName = pageObjectElementName;
    }

    @NotNull
    @Override
    public String getName() {
        return "Create new Page Object element: '" + pageObjectElementName + "'";
    }

    @NotNull
    @Override
    public String getFamilyName() {
        return getName();
    }

    @Override
    public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor problemDescriptor) {
        if (!(problemDescriptor.getPsiElement() instanceof StringLiteralExpression)) {
            return;
        }

        CreatePageObjectElementFile action = new CreatePageObjectElementFile();
        action.createFile(pageObjectElementName, problemDescriptor.getPsiElement().getProject());
    }
}
