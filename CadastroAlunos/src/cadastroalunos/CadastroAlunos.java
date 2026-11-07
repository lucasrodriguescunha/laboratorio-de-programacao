// Aluno: Lucas Rodrigues Cunha
package cadastroalunos;

import cadastroalunos.telas.TelaPrincipal;

import javax.swing.*;

/**
 * Ponto de entrada do sistema (classe principal do projeto): aplica a
 * aparência do sistema operacional e abre a tela principal.
 */
public class CadastroAlunos {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Se não for possível, o Swing continua com a aparência padrão dele.
        }

        // As telas do Swing devem ser criadas na thread de eventos (EDT).
        SwingUtilities.invokeLater(() -> new TelaPrincipal().setVisible(true));
    }
}
