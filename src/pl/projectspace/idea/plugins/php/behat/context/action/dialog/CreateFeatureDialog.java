package pl.projectspace.idea.plugins.php.behat.context.action.dialog;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.Normalizer;

public class CreateFeatureDialog extends DialogWrapper {
    private JPanel contentPane;
    private JTextField name;
    private JTextArea description;
    private JTextField fileName;

    private JLabel nameLabel;
    private JLabel descriptionLabel;
    private JLabel fileNameLabel;

    private Project project;
    private VirtualFile directory;

    private boolean normalizeFileName = true;

    public CreateFeatureDialog(@Nullable Project project) {
        super(project, false);
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
        setTitle("Create new feature file");

        contentPane = new JPanel();
        contentPane.setPreferredSize(new Dimension(480, 300));

        name = new JTextField();
        description = new JTextArea();
        description.setBorder(BorderFactory.createLoweredBevelBorder());
        fileName = new JTextField();

        nameLabel = new JLabel("Feature name");
        descriptionLabel = new JLabel("Feature description");
        fileNameLabel = new JLabel("File name");

        GroupLayout layout = new GroupLayout(contentPane);
        contentPane.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup()
                .addComponent(nameLabel)
                .addComponent(name)
                .addComponent(descriptionLabel)
                .addComponent(description)
                .addComponent(fileNameLabel)
                .addComponent(fileName)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(nameLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)

                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)

                .addComponent(descriptionLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(description)

                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)

                .addComponent(fileNameLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(fileName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        );

        name.addKeyListener(new RefreshFileNameKeyListener());
        fileName.addKeyListener(new DisableFileNameRefreshKeyListener());

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
                    .toLowerCase()
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
