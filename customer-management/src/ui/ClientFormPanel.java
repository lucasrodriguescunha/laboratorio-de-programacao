package ui;

import entities.Client;
import entities.State;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class ClientFormPanel extends JPanel {

    private final JFormattedTextField cpfField;
    private final JTextField nameField;
    private final JFormattedTextField phoneField;
    private final JComboBox<State> stateCombo;
    private final JButton saveButton;
    private final JButton cancelButton;

    public ClientFormPanel() {
        super(new GridBagLayout());
        setBackground(Theme.YELLOW);
        setBorder(BorderFactory.createEmptyBorder(16, 40, 16, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("CADASTRO DE CLIENTE", SwingConstants.CENTER);
        title.setFont(Theme.SUBTITLE_FONT);
        title.setForeground(Theme.DARK_GREEN);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(6, 6, 16, 6);
        add(title, gbc);
        gbc.insets = new Insets(6, 6, 6, 6);

        cpfField = createMaskedField("###.###.###-##");
        nameField = new JTextField(20);
        phoneField = createMaskedField("(##) #####-####");
        stateCombo = new JComboBox<>(State.values());
        stateCombo.setSelectedIndex(-1);

        addRow(gbc, 1, "CPF:", cpfField);
        addRow(gbc, 2, "Nome:", nameField);
        addRow(gbc, 3, "Telefone:", phoneField);
        addRow(gbc, 4, "Estado:", stateCombo);

        saveButton = createButton("Salvar", Theme.DARK_GREEN);
        cancelButton = createButton("Cancelar", Theme.RED);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttons.setOpaque(false);
        buttons.add(saveButton);
        buttons.add(cancelButton);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 6, 6, 6);
        add(buttons, gbc);
    }

    public void onSaveClick(Runnable action) {
        saveButton.addActionListener(e -> {
            if (hasRequiredFields()) {
                action.run();
            }
        });
    }

    public void onCancelClick(Runnable action) {
        cancelButton.addActionListener(e -> action.run());
    }

    public Client collectClient() {
        return new Client(
                cpfField.getText(),
                nameField.getText(),
                phoneField.getText(),
                (State) stateCombo.getSelectedItem()
        );
    }

    public void clear() {
        cpfField.setValue(null);
        nameField.setText("");
        phoneField.setValue(null);
        stateCombo.setSelectedIndex(-1);
    }

    private boolean hasRequiredFields() {
        StringBuilder errors = new StringBuilder();

        if (!isMaskComplete(cpfField)) {
            errors.append("- CPF é obrigatório e deve estar completo.\n");
        }
        if (isBlank(nameField.getText())) {
            errors.append("- Nome é obrigatório.\n");
        }
        if (!isMaskComplete(phoneField)) {
            errors.append("- Telefone é obrigatório e deve estar completo.\n");
        }
        if (stateCombo.getSelectedItem() == null) {
            errors.append("- Estado é obrigatório.\n");
        }

        if (errors.length() > 0) {
            JOptionPane.showMessageDialog(this, errors.toString(), "Campos obrigatórios", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean isMaskComplete(JFormattedTextField field) {
        String text = field.getText();
        return !isBlank(text) && !text.contains("_");
    }

    private boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }

    private JFormattedTextField createMaskedField(String pattern) {
        try {
            MaskFormatter mask = new MaskFormatter(pattern);
            mask.setPlaceholderCharacter('_');
            return new JFormattedTextField(mask);
        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text, Theme.dot(color));
        button.setFont(Theme.BUTTON_FONT);
        button.setForeground(color);
        return button;
    }

    private void addRow(GridBagConstraints gbc, int row, String label, JComponent field) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(Theme.LABEL_FONT);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        add(jLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        add(field, gbc);
    }
}
