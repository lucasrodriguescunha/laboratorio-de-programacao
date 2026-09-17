package ui;

import entities.Person;

import javax.swing.*;
import java.awt.*;

public class ResultDialog extends JDialog {

    public ResultDialog(Frame owner, Person person) {
        super(owner, "Seus dados", true);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addRow(panel, gbc, 0, "CPF:", person.getCpf());
        addRow(panel, gbc, 1, "Nome:", person.getName());
        addRow(panel, gbc, 2, "Endereço:", person.getAddress());
        addRow(panel, gbc, 3, "Estado:", person.getState() != null ? person.getState().getName() : "");
        addRow(panel, gbc, 4, "Cargo:", person.getRole() != null ? person.getRole().getDescription() : "");

        JButton closeButton = new JButton("Fechar");
        closeButton.addActionListener(e -> dispose());

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 6, 6, 6);
        panel.add(closeButton, gbc);

        setContentPane(panel);
        setSize(420, 320);
        setLocationRelativeTo(owner);
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, String value) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);

        JTextField field = new JTextField(value, 20);
        field.setEditable(false);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(field, gbc);
    }
}
