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
 * Tela de alunos: cadastra (INSERT) e lista (SELECT) os registros da tabela
 * aluno. Tem os campos Nome e E-mail, os botões Salvar e Listar e a área de
 * texto (JTextArea) onde a listagem aparece.
 */
public class TelaAluno extends JDialog {

    private final JTextField jTxtNome = new JTextField(28);
    private final JTextField jTxtEmail = new JTextField(28);
    private final JButton jBSalvar = new JButton("Salvar");
    private final JButton jBListar = new JButton("Listar");
    private final JButton jBFechar = new JButton("Fechar");
    private final JTextArea jTxtArea = new JTextArea(12, 50);
    private final JLabel jLblTotal = new JLabel(" ");

    public TelaAluno(Frame dono) {
        // false = não modal: a tela principal continua acessível com esta aberta.
        super(dono, "Alunos - Cadastro e Listagem", false);
        initComponents();
        pack();
        setMinimumSize(getSize());
    }

    private void initComponents() {
        // Cada botão chama o seu método quando é clicado.
        jBSalvar.addActionListener(evt -> jBSalvarActionPerformed(evt));
        jBListar.addActionListener(evt -> jBListarActionPerformed(evt));
        jBFechar.addActionListener(evt -> setVisible(false));
        // Enter em qualquer campo aciona o botão Salvar.
        getRootPane().setDefaultButton(jBSalvar);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        botoes.add(jBSalvar);
        botoes.add(jBListar);

        JPanel cadastro = new JPanel(new BorderLayout(0, 8));
        cadastro.add(Formulario.criar("Dados do aluno",
                new String[]{"Nome:", "E-mail:"}, jTxtNome, jTxtEmail), BorderLayout.CENTER);
        cadastro.add(botoes, BorderLayout.SOUTH);

        JPanel rodape = new JPanel(new BorderLayout());
        rodape.add(jLblTotal, BorderLayout.WEST);
        rodape.add(jBFechar, BorderLayout.EAST);

        JPanel conteudo = new JPanel(new BorderLayout(0, 10));
        conteudo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        conteudo.add(cadastro, BorderLayout.NORTH);
        conteudo.add(Formulario.listagem("Alunos cadastrados", jTxtArea), BorderLayout.CENTER);
        conteudo.add(rodape, BorderLayout.SOUTH);
        setContentPane(conteudo);
    }

    // Botão Salvar: grava o aluno na tabela aluno com INSERT.
    private void jBSalvarActionPerformed(java.awt.event.ActionEvent evt) {
        String nome = jTxtNome.getText().trim();
        String email = jTxtEmail.getText().trim();

        // Confere os campos antes de ir ao banco: as colunas são NOT NULL e
        // aceitam no máximo 100 caracteres.
        if (nome.isEmpty()) {
            Formulario.aviso(this, "Informe o nome do aluno.", jTxtNome);
            return;
        }
        if (nome.length() > Formulario.TAMANHO_MAXIMO) {
            Formulario.aviso(this, "O nome deve ter no máximo 100 caracteres.", jTxtNome);
            return;
        }
        if (email.isEmpty()) {
            Formulario.aviso(this, "Informe o e-mail do aluno.", jTxtEmail);
            return;
        }
        if (email.length() > Formulario.TAMANHO_MAXIMO) {
            Formulario.aviso(this, "O e-mail deve ter no máximo 100 caracteres.", jTxtEmail);
            return;
        }
        if (!Formulario.emailValido(email)) {
            Formulario.aviso(this, "Informe um e-mail válido (exemplo: nome@email.com).", jTxtEmail);
            return;
        }

        String sql = "INSERT INTO aluno (nome, email) VALUES (?, ?)";
        // try-with-resources: a Connection e o PreparedStatement são fechados
        // no final do bloco, mesmo se acontecer um erro.
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            // Cada "?" do SQL recebe um valor, na ordem: 1 = nome, 2 = email.
            stmt.setString(1, nome);
            stmt.setString(2, email);
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Aluno cadastrado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            jTxtNome.setText("");
            jTxtEmail.setText("");
            jTxtNome.requestFocusInWindow();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar aluno: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Botão Listar: busca todos os alunos com SELECT e mostra na área de texto.
    private void jBListarActionPerformed(java.awt.event.ActionEvent evt) {
        String sql = "SELECT id, nome, email FROM aluno ORDER BY id";
        try (Connection con = ConnectionFactory.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            jTxtArea.setText("");
            int total = 0;
            // rs.next() avança para a próxima linha do resultado e devolve
            // false quando não há mais linhas.
            while (rs.next()) {
                jTxtArea.append(rs.getInt("id") + " - Nome: "
                        + rs.getString("nome") + " - Email: "
                        + rs.getString("email") + "\n");
                total++;
            }
            if (total == 0) {
                jTxtArea.setText("Nenhum aluno cadastrado.");
            }
            jTxtArea.setCaretPosition(0);
            jLblTotal.setText(total + " aluno(s) listado(s)");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao listar alunos: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
