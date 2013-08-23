package pl.projectspace.idea.plugins.php.behat.core;

import com.intellij.ide.IconProvider;
import com.intellij.openapi.util.Iconable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.jetbrains.php.lang.psi.PhpFile;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.utils.BehatUtils;

import javax.swing.*;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class BehatIconProvider extends IconProvider {

    private BehatUtils utils;

    @Nullable
    @Override
    public Icon getIcon(@NotNull PsiElement element, @Iconable.IconFlags int i) {
        if (!BehatProject.isEnabled()) {
            return null;
        }

        BehatUtils validator = getUtils(element);
        if ((element instanceof PhpFile)) {
            for (PsiNamedElement el : ((PhpFile)element).getTopLevelDefs().values()) {
                if (el instanceof PhpClass && validator.isBehatClass((PhpClass) el)) {
                    return BehatIcons.File;
                }
            }
        }
        return null;
    }

    private BehatUtils getUtils(PsiElement element) {
        if (utils == null) {
            utils = element.getProject().getComponent(BehatProject.class).getService(BehatUtils.class);
        }

        return utils;
    }
}
