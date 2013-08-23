package pl.projectspace.idea.plugins.php.behat.extensions.pageobject.action.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;
import pl.projectspace.idea.plugins.commons.php.psi.exceptions.MissingElementException;
import pl.projectspace.idea.plugins.php.behat.BehatProject;
import pl.projectspace.idea.plugins.php.behat.extensions.pageobject.locator.PageElementLocator;

import javax.swing.*;
import java.awt.*;

public class CreatePageObjectDialog extends DialogWrapper {
    private final Project project;
    private VirtualFile destinationDirectory;
    private String title;
    private String name;

    private JPanel contentPane;
    private JTextField nameField;
    private JLabel nameLabel;
    private JLabel locationLabel;

    public CreatePageObjectDialog(@Nullable Project project, VirtualFile destinationDirectory, String title, String name) {
        super(project);
        this.project = project;
        this.destinationDirectory = destinationDirectory;
        this.title = title;
        this.name = name;
        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        setTitle(title);
        setResizable(false);

        contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(480, 80));

        nameField = new JTextField();
        nameLabel = new JLabel(name);
        locationLabel = new JLabel("Element location: " + destinationDirectory.getPath().replace(project.getBasePath(), ""));

        GroupLayout layout = new GroupLayout(contentPane);
        contentPane.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addComponent(nameLabel)
                .addComponent(nameField)
                .addComponent(locationLabel)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addComponent(nameField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(locationLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        return contentPane;
    }

    public String getName() {
        return nameField.getText().trim();
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        String name = nameField.getText().trim();
        if (name.equals("")) {
            return new ValidationInfo("Element name can not be empty.", nameField);
        }

        if (!name.matches("[a-zA-z0-9_\\-]+")) {
            return new ValidationInfo("Element name should contain only letters, numbers and '-', '_' signs.", nameField);
        }

        try {
            project.getComponent(BehatProject.class).getService(PageElementLocator.class).locate(name);
            return new ValidationInfo("Element given name already exists.", nameField);
        } catch (MissingElementException e) {
        }

        return null;
    }
}
