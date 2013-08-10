package pl.projectspace.idea.plugins.php.behat.psi.referene;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.ProjectScope;
import com.intellij.util.ArrayUtil;
import com.intellij.util.IncorrectOperationException;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import com.jetbrains.php.lang.psi.elements.impl.StringLiteralExpressionImpl;
import org.jetbrains.annotations.NotNull;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class PageObjectReference implements PsiReference {

    private PhpClass pageObject;
    /**
     * The template string from e.x. a controller
     */
    private StringLiteralExpression name;

    /**
     *
     * @param pageObject
     * @param name
     */
    public PageObjectReference(PhpClass pageObject, final StringLiteralExpression name) {
        this.pageObject = pageObject;
        this.name = name;
    }

    /**
     * @see com.intellij.psi.PsiReference#getElement()
     * @return the full source element
     */
    @Override
    public PsiElement getElement() {
        return name;
    }

    /**
     * @see com.intellij.psi.PsiReference#getRangeInElement()
     * @return the full range incl. quotes.
     */
    @Override
    public TextRange getRangeInElement() {
        return new TextRange(1, this.name.getTextLength() - 1);
    }

    /**
     * @see com.intellij.psi.PsiReference#resolve()
     * @return the resolved template file or null
     */
    @Override
    public PsiElement resolve() {
        return pageObject;
    }

    /**
     * @see com.intellij.psi.PsiReference#getCanonicalText()
     * @return Plain text representation.
     */
    @NotNull
    @Override
    public String getCanonicalText() {
        return this.name.getContents();
    }

    /**
     * @see com.intellij.psi.PsiReference#handleElementRename(String)
     * @param newElementName
     * @return the new string literal with the new text
     * @throws IncorrectOperationException
     */
    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        ASTNode node = name.getNode();
        StringLiteralExpressionImpl se = new StringLiteralExpressionImpl(node);

        String[] parts = name.getText().split(":");
        StringBuilder sb = new StringBuilder(parts[0]).append(":").append(parts[1]).append(":");
        sb.append(newElementName);
        se.updateText(sb.toString());
        this.name = se;
        return name;
    }

    @Override
    public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
        return resolve();
    }

    @Override
    public boolean isReferenceTo(PsiElement element) {
        if (element instanceof PhpClass) {
            ((PhpClass) element).getName().equals(name.getContents());
        }
        return false;
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        return ArrayUtil.EMPTY_OBJECT_ARRAY;
    }

    @Override
    public boolean isSoft() {
        return true;
    }

}
