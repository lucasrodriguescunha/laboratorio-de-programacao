// Aluno: Lucas Rodrigues Cunha
package ui;

import entities.Person;
import entities.Role;
import entities.State;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

/**
 * O painel do formulário (JPanel): campos, rótulos, botão e validação.
 *
 * Ele cuida só da tela. Quem decide o que fazer no clique do botão é a
 * janela, que registra a ação pelo método onPrintClick.
 */
public class PersonFormPanel extends JPanel {

    private final JFormattedTextField cpfField;
    private final JTextField nameField;
    private final JTextField addressField;
    private final JComboBox<State> stateCombo;
    private final JComboBox<Role> roleCombo;
    private final JButton printButton;

    public PersonFormPanel() {
        // GridBagLayout organiza os componentes em uma grade de linhas e colunas.
        super(new GridBagLayout());
        // Margem interna de 16 px entre a borda da janela e os campos.
        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        // O gbc diz onde cada componente fica na grade; insets é o espaço em
        // volta de cada um e HORIZONTAL faz o campo ocupar a largura da célula.
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cpfField = createCpfField();
        nameField = new JTextField(20);
        addressField = new JTextField(20);
        // Os combos são preenchidos com as constantes dos enums e começam
        // sem nada selecionado (índice -1), para o usuário ter que escolher.
        stateCombo = new JComboBox<>(State.values());
        stateCombo.setSelectedIndex(-1);
        roleCombo = new JComboBox<>(Role.values());
        roleCombo.setSelectedIndex(-1);
        printButton = new JButton("Imprimir Dados");

        // Uma linha da grade para cada campo: rótulo à esquerda, campo à direita.
        addRow(gbc, 0, "CPF:", cpfField);
        addRow(gbc, 1, "Nome:", nameField);
        addRow(gbc, 2, "Endereço:", addressField);
        addRow(gbc, 3, "Estado:", stateCombo);
        addRow(gbc, 4, "Cargo:", roleCombo);

        // O botão fica na última linha, ocupando as duas colunas.
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(16, 6, 6, 6);
        add(printButton, gbc);
    }

    // Registra a ação do botão. Ela só é executada se a validação passar.
    public void onPrintClick(Runnable action) {
        printButton.addActionListener(e -> {
            if (hasRequiredFields()) {
                action.run();
            }
        });
    }

    // Cria um Person com o que está digitado e selecionado nos campos.
    public Person collectPerson() {
        return new Person(
                cpfField.getText(),
                nameField.getText(),
                addressField.getText(),
                (State) stateCombo.getSelectedItem(),
                (Role) roleCombo.getSelectedItem()
        );
    }

    // Validação: todos os campos são obrigatórios. Os erros são acumulados
    // e exibidos de uma vez só, em uma única mensagem de aviso.
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

    // Se ainda existe "_" no texto, algum dígito do CPF não foi preenchido.
    private boolean isCpfComplete() {
        String text = cpfField.getText();
        return !isBlank(text) && !text.contains("_");
    }

    private boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }

    // Campo de CPF com máscara: cada # aceita apenas um dígito, e os pontos
    // e o traço já aparecem no campo. As posições vazias são mostradas com "_".
    private JFormattedTextField createCpfField() {
        try {
            MaskFormatter mask = new MaskFormatter("###.###.###-##");
            mask.setPlaceholderCharacter('_');
            return new JFormattedTextField(mask);
        } catch (ParseException e) {
            // Só ocorreria se a máscara fosse inválida; nesse caso, campo sem máscara.
            return new JFormattedTextField();
        }
    }

    // Adiciona uma linha ao formulário: o rótulo na coluna 0 e o campo na
    // coluna 1. O weightx = 1 faz o campo ficar com a largura que sobrar.
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
