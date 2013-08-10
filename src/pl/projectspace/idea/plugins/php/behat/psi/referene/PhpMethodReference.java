package pl.projectspace.idea.plugins.php.behat.psi.referene;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.*;
import com.intellij.util.IncorrectOperationException;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.Method;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.cucumber.psi.GherkinStep;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PhpMethodReference implements PsiReference {

    private PsiElement method;
    private PsiElement name;

    public PhpMethodReference(@NotNull Method method, @NotNull final PsiElement name) {
        this.method = method;
        this.name = name;
    }

    @Override
    public PsiElement getElement() {
        return name;
    }

    @Override
    public TextRange getRangeInElement() {
        return new TextRange(1, this.name.getTextLength() - 1);
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        return method;
    }

    @NotNull
    @Override
    public String getCanonicalText() {
        if (name instanceof StringLiteralExpression) {
            return ((StringLiteralExpression)name).getContents();
        }
        else {
            return ((GherkinStep)name).getText();
        }
    }

    @Override
    public PsiElement handleElementRename(String s) throws IncorrectOperationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement psiElement) throws IncorrectOperationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isReferenceTo(PsiElement psiElement) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return new Object[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isSoft() {
        return true;
    }
}
