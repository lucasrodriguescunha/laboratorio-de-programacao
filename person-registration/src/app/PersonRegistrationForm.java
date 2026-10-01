// Aluno: Lucas Rodrigues Cunha
package app;

import ui.PersonFormPanel;
import ui.ResultDialog;

import javax.swing.*;

/**
 * A janela principal do sistema (JFrame).
 *
 * Ela não desenha os campos: só configura a janela, coloca o painel do
 * formulário dentro dela e define o que acontece quando o botão é clicado.
 */
public class PersonRegistrationForm extends JFrame {

    public PersonRegistrationForm() {
        // Configuração da janela: título, tamanho fixo e posição centralizada.
        setTitle("Formulário de Cadastro");
        // Fechar a janela no "X" encerra o programa.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 320);
        setLocationRelativeTo(null);
        setResizable(false);

        // Ao clicar em "Imprimir Dados", o painel entrega um Person com o que
        // foi digitado e o ResultDialog mostra esses dados em uma mensagem.
        PersonFormPanel panel = new PersonFormPanel();
        panel.onPrintClick(() -> ResultDialog.show(this, panel.collectPerson()));

        // O painel do formulário passa a ser o conteúdo da janela.
        setContentPane(panel);
    }
}
