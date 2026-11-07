// Aluno: Lucas Rodrigues Cunha
package cadastroalunos.telas;

import cadastroalunos.conexao.ConnectionFactory;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 * Tela principal do sistema (JFrame): o menu com os botões que abrem as
 * telas de alunos, professores e cursos, e o status da conexão com o banco.
 */
public class TelaPrincipal extends JFrame {

    private static final Color VERDE = new Color(0, 128, 0);
    private static final Color VERMELHO = new Color(178, 34, 34);

    // As outras telas são criadas uma vez e reaproveitadas a cada clique.
    private final TelaAluno telaAluno;
    private final TelaCadastroProfessor telaCadastroProfessor;
    private final TelaListagemProfessor telaListagemProfessor;
    private final TelaCadastroCurso telaCadastroCurso;
    private final TelaListagemCurso telaListagemCurso;

    private final JButton jBAlunos = new JButton("Cadastrar e Listar Alunos");
    private final JButton jBCadastrarProfessor = new JButton("Cadastrar Professor");
    private final JButton jBListarProfessores = new JButton("Listar Professores");
    private final JButton jBCadastrarCurso = new JButton("Cadastrar Curso");
    private final JButton jBListarCursos = new JButton("Listar Cursos");
    private final JButton jBTestarConexao = new JButton("Testar Conexão");
    private final JButton jBSair = new JButton("Sair");
    private final JLabel jLblStatus = new JLabel(" ", SwingConstants.CENTER);

    public TelaPrincipal() {
        super("Sistema Escola - CadastroAlunos");

        telaAluno = new TelaAluno(this);
        telaCadastroProfessor = new TelaCadastroProfessor(this);
        telaListagemProfessor = new TelaListagemProfessor(this);
        telaCadastroCurso = new TelaCadastroCurso(this);
        telaListagemCurso = new TelaListagemCurso(this);

        initComponents();
        // Fechar a tela principal no "X" encerra o programa.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);

        // Ao abrir, confere se o banco está acessível e mostra o resultado no rodapé.
        verificarConexao(false);
    }

    private void initComponents() {
        jBAlunos.addActionListener(evt -> abrirTela(telaAluno));
        jBCadastrarProfessor.addActionListener(evt -> abrirTela(telaCadastroProfessor));
        jBListarProfessores.addActionListener(evt -> abrirTela(telaListagemProfessor));
        jBCadastrarCurso.addActionListener(evt -> abrirTela(telaCadastroCurso));
        jBListarCursos.addActionListener(evt -> abrirTela(telaListagemCurso));
        jBTestarConexao.addActionListener(evt -> verificarConexao(true));
        jBSair.addActionListener(evt -> System.exit(0));

        JLabel jLblTitulo = new JLabel("Sistema Escola", SwingConstants.CENTER);
        jLblTitulo.setFont(jLblTitulo.getFont().deriveFont(Font.BOLD, 22f));
        JLabel jLblSubtitulo = new JLabel("Escolha a tela que deseja abrir", SwingConstants.CENTER);

        JPanel cabecalho = new JPanel(new GridLayout(2, 1, 0, 4));
        cabecalho.add(jLblTitulo);
        cabecalho.add(jLblSubtitulo);

        // Um grupo de botões para cada tabela do banco e um para o sistema.
        JPanel menu = new JPanel(new GridLayout(0, 1, 0, 10));
        menu.add(grupo("Alunos", jBAlunos));
        menu.add(grupo("Professores", jBCadastrarProfessor, jBListarProfessores));
        menu.add(grupo("Cursos", jBCadastrarCurso, jBListarCursos));
        menu.add(grupo("Sistema", jBTestarConexao, jBSair));

        JPanel conteudo = new JPanel(new BorderLayout(0, 14));
        conteudo.setBorder(BorderFactory.createEmptyBorder(16, 20, 12, 20));
        conteudo.add(cabecalho, BorderLayout.NORTH);
        conteudo.add(menu, BorderLayout.CENTER);
        conteudo.add(jLblStatus, BorderLayout.SOUTH);
        setContentPane(conteudo);
    }

    // Painel com borda e título contendo os botões lado a lado.
    private static JPanel grupo(String titulo, JButton... botoes) {
        JPanel painel = new JPanel(new GridLayout(1, 0, 10, 0));
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(titulo),
                BorderFactory.createEmptyBorder(4, 8, 8, 8)));
        for (JButton botao : botoes) {
            painel.add(botao);
        }
        return painel;
    }

    // Mostra a tela escolhida. Se ela já estiver aberta, só a traz para frente.
    private void abrirTela(JDialog tela) {
        if (!tela.isVisible()) {
            tela.setLocationRelativeTo(this);
        }
        tela.setVisible(true);
        tela.toFront();
    }

    // Abre e fecha uma conexão para saber se o banco está acessível. Com
    // mostrarMensagem = true (botão Testar Conexão), também exibe os detalhes.
    private void verificarConexao(boolean mostrarMensagem) {
        try (Connection con = ConnectionFactory.getConnection()) {
            jLblStatus.setText("Banco de dados: conectado");
            jLblStatus.setForeground(VERDE);
            if (mostrarMensagem) {
                DatabaseMetaData dados = con.getMetaData();
                JOptionPane.showMessageDialog(this, "Conexão realizada com sucesso!\n\n"
                        + "Banco: " + con.getCatalog() + "\n"
                        + "Servidor: " + dados.getDatabaseProductName() + " "
                        + dados.getDatabaseProductVersion() + "\n"
                        + "Driver: " + dados.getDriverName() + " " + dados.getDriverVersion() + "\n"
                        + "Usuário: " + dados.getUserName(),
                        "Testar Conexão", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            jLblStatus.setText("Banco de dados: sem conexão");
            jLblStatus.setForeground(VERMELHO);
            if (mostrarMensagem) {
                JOptionPane.showMessageDialog(this, e.getMessage(),
                        "Testar Conexão", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
