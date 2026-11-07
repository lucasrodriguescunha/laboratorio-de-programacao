// Aluno: Lucas Rodrigues Cunha
package cadastroalunos.telas;

import cadastroalunos.conexao.ConnectionFactory;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Tela de cadastro de curso: campos Nome e Carga horária e o botão Inserir,
 * que grava o registro na tabela curso com INSERT.
 */
public class TelaCadastroCurso extends JDialog {

    private final JTextField jTxtNome = new JTextField(28);
    private final JTextField jTxtCargaHoraria = new JTextField(28);
    private final JButton jBInserir = new JButton("Inserir");
    private final JButton jBFechar = new JButton("Fechar");

    public TelaCadastroCurso(Frame dono) {
        super(dono, "Cadastro de Curso", false);
        initComponents();
        pack();
        setResizable(false);
    }

    private void initComponents() {
        jTxtCargaHoraria.setToolTipText("Total de horas do curso, só números (exemplo: 60)");
        jBInserir.addActionListener(evt -> jBInserirActionPerformed(evt));
        jBFechar.addActionListener(evt -> setVisible(false));
        getRootPane().setDefaultButton(jBInserir);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botoes.add(jBInserir);
        botoes.add(jBFechar);

        JPanel conteudo = new JPanel(new BorderLayout(0, 10));
        conteudo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        conteudo.add(Formulario.criar("Dados do curso",
                new String[]{"Nome:", "Carga horária (horas):"}, jTxtNome, jTxtCargaHoraria),
                BorderLayout.CENTER);
        conteudo.add(botoes, BorderLayout.SOUTH);
        setContentPane(conteudo);
    }

    // Botão Inserir: grava o curso na tabela curso com INSERT.
    private void jBInserirActionPerformed(java.awt.event.ActionEvent evt) {
        String nome = jTxtNome.getText().trim();
        String textoCargaHoraria = jTxtCargaHoraria.getText().trim();

        if (nome.isEmpty()) {
            Formulario.aviso(this, "Informe o nome do curso.", jTxtNome);
            return;
        }
        if (nome.length() > Formulario.TAMANHO_MAXIMO) {
            Formulario.aviso(this, "O nome deve ter no máximo 100 caracteres.", jTxtNome);
            return;
        }
        if (textoCargaHoraria.isEmpty()) {
            Formulario.aviso(this, "Informe a carga horária do curso.", jTxtCargaHoraria);
            return;
        }

        // A coluna carga_horaria é INT: o texto digitado precisa virar um número inteiro.
        int cargaHoraria;
        try {
            cargaHoraria = Integer.parseInt(textoCargaHoraria);
        } catch (NumberFormatException e) {
            Formulario.aviso(this, "A carga horária deve ser um número inteiro de horas (exemplo: 60).",
                    jTxtCargaHoraria);
            return;
        }
        if (cargaHoraria <= 0) {
            Formulario.aviso(this, "A carga horária deve ser maior que zero.", jTxtCargaHoraria);
            return;
        }

        String sql = "INSERT INTO curso (nome, carga_horaria) VALUES (?, ?)";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nome);
            // setInt porque a coluna é numérica (INT), e não texto.
            stmt.setInt(2, cargaHoraria);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Curso cadastrado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            jTxtNome.setText("");
            jTxtCargaHoraria.setText("");
            jTxtNome.requestFocusInWindow();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar curso: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
