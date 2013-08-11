package pl.projectspace.idea.plugins.php.behat.psi.element.lookup;

import com.intellij.codeInsight.lookup.LookupElement;
import org.jetbrains.annotations.NotNull;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class SimpleTextLookup extends LookupElement {

    private String text;

    public SimpleTextLookup(String text){
        this.text = text;
    }

    @NotNull
    @Override
    public String getLookupString() {
        return text;
    }
}
