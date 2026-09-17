package ui;

import entities.Person;
import entities.Role;
import entities.State;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class PersonFormPanel extends JPanel {

    private final JFormattedTextField cpfField;
    private final JTextField nameField;
    private final JTextField addressField;
    private final JComboBox<State> stateCombo;
    private final JComboBox<Role> roleCombo;
    private final JButton printButton;

    public PersonFormPanel() {
        super(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cpfField = createCpfField();
        nameField = new JTextField(20);
        addressField = new JTextField(20);
        stateCombo = new JComboBox<>(State.values());
        stateCombo.setSelectedIndex(-1);
        roleCombo = new JComboBox<>(Role.values());
        roleCombo.setSelectedIndex(-1);
        printButton = new JButton("Imprimir Dados");

        addRow(gbc, 0, "CPF:", cpfField);
        addRow(gbc, 1, "Nome:", nameField);
        addRow(gbc, 2, "Endereço:", addressField);
        addRow(gbc, 3, "Estado:", stateCombo);
        addRow(gbc, 4, "Cargo:", roleCombo);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 6, 6, 6);
        add(printButton, gbc);
    }

    public void onPrintClick(Runnable action) {
        printButton.addActionListener(e -> {
            if (hasRequiredFields()) {
                action.run();
            }
        });
    }

    public Person collectPerson() {
        return new Person(
                cpfField.getText(),
                nameField.getText(),
                addressField.getText(),
                (State) stateCombo.getSelectedItem(),
                (Role) roleCombo.getSelectedItem()
        );
    }

    private boolean hasRequiredFields() {
        StringBuilder errors = new StringBuilder();

        if (!isCpfComplete()) {
            errors.append("- CPF é obrigatório e deve estar completo.\n");
        }
        if (isBlank(nameField.getText())) {
            errors.append("- Nome é obrigatório.\n");
        }
        if (isBlank(addressField.getText())) {
            errors.append("- Endereço é obrigatório.\n");
        }
        if (stateCombo.getSelectedItem() == null) {
            errors.append("- Estado é obrigatório.\n");
        }
        if (roleCombo.getSelectedItem() == null) {
            errors.append("- Cargo é obrigatório.\n");
        }

        if (errors.length() > 0) {
            JOptionPane.showMessageDialog(this, errors.toString(), "Campos obrigatórios", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean isCpfComplete() {
        String text = cpfField.getText();
        return !isBlank(text) && !text.contains("_");
    }

    private boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }

    private JFormattedTextField createCpfField() {
        try {
            MaskFormatter mask = new MaskFormatter("###.###.###-##");
            mask.setPlaceholderCharacter('_');
            return new JFormattedTextField(mask);
        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }

    private void addRow(GridBagConstraints gbc, int row, String label, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        add(field, gbc);
    }
}
