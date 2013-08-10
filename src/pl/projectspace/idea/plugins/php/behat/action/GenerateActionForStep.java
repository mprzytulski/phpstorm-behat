package pl.projectspace.idea.plugins.php.behat.action;

import com.intellij.lang.Language;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;

/**
 * @author Michal Przytulski <michal@przytulski.pl>
 */
public class GenerateActionForStep extends AnAction {
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
    }

    @Override
    public void update(AnActionEvent e) {
        Language lang = e.getData(LangDataKeys.LANGUAGE);
        if (!lang.is(Language.findLanguageByID("Gherkin"))) {
            e.getPresentation().setVisible(false);
        }
//        StepResolver resolver = ServiceManager.getService(e.getProject(), StepResolver.class);

//        e.getData(Gherkin)

        System.out.println("Generate");
//        resolver.getImplementationMethodFor(e.get)
    }
}
