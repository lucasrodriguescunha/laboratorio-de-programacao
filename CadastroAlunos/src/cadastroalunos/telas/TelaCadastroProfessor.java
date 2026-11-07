// Aluno: Lucas Rodrigues Cunha
package cadastroalunos.telas;

import cadastroalunos.conexao.ConnectionFactory;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Tela de cadastro de professor: campos Nome e Disciplina e o botão Inserir,
 * que grava o registro na tabela professor com INSERT.
 */
public class TelaCadastroProfessor extends JDialog {

    private final JTextField jTxtNome = new JTextField(28);
    private final JTextField jTxtDisciplina = new JTextField(28);
    private final JButton jBInserir = new JButton("Inserir");
    private final JButton jBFechar = new JButton("Fechar");

    public TelaCadastroProfessor(Frame dono) {
        super(dono, "Cadastro de Professor", false);
        initComponents();
        pack();
        setResizable(false);
    }

    private void initComponents() {
        jBInserir.addActionListener(evt -> jBInserirActionPerformed(evt));
        jBFechar.addActionListener(evt -> setVisible(false));
        getRootPane().setDefaultButton(jBInserir);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botoes.add(jBInserir);
        botoes.add(jBFechar);

        JPanel conteudo = new JPanel(new BorderLayout(0, 10));
        conteudo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        conteudo.add(Formulario.criar("Dados do professor",
                new String[]{"Nome:", "Disciplina:"}, jTxtNome, jTxtDisciplina), BorderLayout.CENTER);
        conteudo.add(botoes, BorderLayout.SOUTH);
        setContentPane(conteudo);
    }

    // Botão Inserir: grava o professor na tabela professor com INSERT.
    private void jBInserirActionPerformed(java.awt.event.ActionEvent evt) {
        String nome = jTxtNome.getText().trim();
        String disciplina = jTxtDisciplina.getText().trim();

        if (nome.isEmpty()) {
            Formulario.aviso(this, "Informe o nome do professor.", jTxtNome);
            return;
        }
        if (nome.length() > Formulario.TAMANHO_MAXIMO) {
            Formulario.aviso(this, "O nome deve ter no máximo 100 caracteres.", jTxtNome);
            return;
        }
        if (disciplina.isEmpty()) {
            Formulario.aviso(this, "Informe a disciplina do professor.", jTxtDisciplina);
            return;
        }
        if (disciplina.length() > Formulario.TAMANHO_MAXIMO) {
            Formulario.aviso(this, "A disciplina deve ter no máximo 100 caracteres.", jTxtDisciplina);
            return;
        }

        String sql = "INSERT INTO professor (nome, disciplina) VALUES (?, ?)";
        // Mesmo padrão do botão Salvar de aluno: conexão pela ConnectionFactory,
        // PreparedStatement com "?" e fechamento automático no try-with-resources.
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, disciplina);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Professor cadastrado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            jTxtNome.setText("");
            jTxtDisciplina.setText("");
            jTxtNome.requestFocusInWindow();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar professor: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
