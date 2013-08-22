package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.action.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import org.jetbrains.annotations.Nullable;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.locator.PageElementLocator;

import javax.swing.*;

public class CreatePageObjectDialog extends DialogWrapper {
    private final Project project;
    private String title;
    private String name;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField nameField;
    private JLabel fieldLabel;

    public CreatePageObjectDialog(@Nullable Project project, String title, String name) {
        super(project);
        this.project = project;
        this.title = title;
        this.name = name;
        setTitle(title);
        fieldLabel.setText(name);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return contentPane;
    }

    public String getName() {
        return nameField.getText();
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        String name = nameField.getText().trim();
        if (name.equals("")) {
            return new ValidationInfo("Element name can not be empty.", nameField);
        }

        try {
            project.getComponent(BehatProject.class).getService(PageElementLocator.class).locate(name);
            return new ValidationInfo("Element given name already exists.", nameField);
        } catch (MissingElementException e) {
        }

        return null;
    }
}
