// Aluno: Lucas Rodrigues Cunha
package cadastroalunos.telas;

import cadastroalunos.conexao.ConnectionFactory;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Tela de listagem de professores: o botão Listar busca os registros da
 * tabela professor com SELECT e os mostra na área de texto (JTextArea).
 */
public class TelaListagemProfessor extends JDialog {

    private final JButton jBListar = new JButton("Listar");
    private final JButton jBFechar = new JButton("Fechar");
    private final JTextArea jTxtArea = new JTextArea(14, 50);
    private final JLabel jLblTotal = new JLabel(" ");

    public TelaListagemProfessor(Frame dono) {
        super(dono, "Listagem de Professores", false);
        initComponents();
        pack();
        setMinimumSize(getSize());
    }

    private void initComponents() {
        jBListar.addActionListener(evt -> jBListarActionPerformed(evt));
        jBFechar.addActionListener(evt -> setVisible(false));
        getRootPane().setDefaultButton(jBListar);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botoes.add(jBListar);
        botoes.add(jBFechar);

        JPanel rodape = new JPanel(new BorderLayout());
        rodape.add(jLblTotal, BorderLayout.WEST);
        rodape.add(botoes, BorderLayout.EAST);

        JPanel conteudo = new JPanel(new BorderLayout(0, 10));
        conteudo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        conteudo.add(Formulario.listagem("Professores cadastrados", jTxtArea), BorderLayout.CENTER);
        conteudo.add(rodape, BorderLayout.SOUTH);
        setContentPane(conteudo);
    }

    // Botão Listar: busca todos os professores e mostra um por linha.
    private void jBListarActionPerformed(java.awt.event.ActionEvent evt) {
        String sql = "SELECT id, nome, disciplina FROM professor ORDER BY id";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            jTxtArea.setText("");
            int total = 0;
            while (rs.next()) {
                jTxtArea.append(rs.getInt("id") + " - Nome: "
                        + rs.getString("nome") + " - Disciplina: "
                        + rs.getString("disciplina") + "\n");
                total++;
            }
            if (total == 0) {
                jTxtArea.setText("Nenhum professor cadastrado.");
            }
            jTxtArea.setCaretPosition(0);
            jLblTotal.setText(total + " professor(es) listado(s)");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar professores: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
