package pl.projectspace.idea.plugins.php.behat.context.action.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.*;
import java.text.Normalizer;

public class CreateFeatureDialog extends DialogWrapper {
    private JPanel contentPane;
    private JTextField name;
    private JTextPane description;
    private JTextField fileName;
    private Project project;
    private VirtualFile directory;

    private boolean normalizeFileName = true;

    public CreateFeatureDialog(@Nullable Project project) {
        super(project);
        setTitle("Create new feature file");

        name.addKeyListener(new RefreshFileNameKeyListener());
        fileName.addKeyListener(new DisableFileNameRefreshKeyListener());
        init();
    }

    public String getName() {
        return name.getText();
    }

    public String getDescription() {
        return description.getText();
    }

    public String getFileName(){
        return fileName.getText();
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        if (name.getText().trim().equals("")) {
            return new ValidationInfo("You need to provide feature name.", name);
        }

        if (description.getText().trim().equals("")) {
            return new ValidationInfo("You need to provide description.", description);
        }

        if (!fileName.getText().endsWith(".feature")) {
            return new ValidationInfo("Feature file name must ends with '.feature'.", fileName);
        }

        if (fileName.getText().trim().length() <= 8) {
            return new ValidationInfo("You need to provide feature file name.", fileName);
        }

        return null;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return contentPane;
    }

    private class RefreshFileNameKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (!normalizeFileName) {
                return;
            }
            fileName.setText(
                Normalizer.normalize(
                    name.getText(), Normalizer.Form.NFD
                ).replaceAll("[^\\p{ASCII}]","").replaceAll(" ", "-")
                    .concat(".feature")
            );
        }
    }

    private class DisableFileNameRefreshKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            normalizeFileName = false;
        }
    }
}
