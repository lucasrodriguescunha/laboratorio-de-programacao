// Aluno: Lucas Rodrigues Cunha
package cadastroalunos.telas;

import javax.swing.*;
import java.awt.*;

/**
 * Monta as partes que se repetem nas telas: o painel com rótulos e campos,
 * a área de texto da listagem e a mensagem de aviso de campo inválido.
 */
final class Formulario {

    // Tamanho máximo de nome, e-mail e disciplina: as colunas são VARCHAR(100).
    static final int TAMANHO_MAXIMO = 100;

    private Formulario() {
    }

    // Painel com borda e título; cada rótulo fica à esquerda do seu campo.
    static JPanel criar(String titulo, String[] rotulos, JTextField... campos) {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(titulo),
                BorderFactory.createEmptyBorder(4, 6, 6, 6)));

        // O gbc diz em que linha e coluna da grade cada componente fica.
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        for (int i = 0; i < rotulos.length; i++) {
            JLabel rotulo = new JLabel(rotulos[i]);
            rotulo.setLabelFor(campos[i]);

            // Coluna 0: o rótulo, alinhado à direita.
            gbc.gridy = i;
            gbc.gridx = 0;
            gbc.weightx = 0;
            gbc.fill = GridBagConstraints.NONE;
            gbc.anchor = GridBagConstraints.LINE_END;
            painel.add(rotulo, gbc);

            // Coluna 1: o campo, ocupando toda a largura que sobrar.
            gbc.gridx = 1;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            painel.add(campos[i], gbc);
        }
        return painel;
    }

    // Área de texto somente leitura, dentro de uma barra de rolagem.
    static JScrollPane listagem(String titulo, JTextArea area) {
        area.setEditable(false);
        // Usa a mesma fonte dos rótulos: a fonte padrão do JTextArea no Windows
        // é monoespaçada e larga, e cortaria as linhas mais compridas.
        area.setFont(UIManager.getFont("Label.font"));
        area.setMargin(new Insets(4, 6, 4, 6));
        JScrollPane rolagem = new JScrollPane(area);
        rolagem.setBorder(BorderFactory.createTitledBorder(titulo));
        return rolagem;
    }

    // Mostra o aviso e coloca o cursor no campo que precisa ser corrigido.
    static void aviso(Component tela, String mensagem, JTextField campo) {
        JOptionPane.showMessageDialog(tela, mensagem, "Atenção", JOptionPane.WARNING_MESSAGE);
        campo.requestFocusInWindow();
    }

    // Confere um e-mail no formato texto@texto.texto, sem espaços.
    static boolean emailValido(String email) {
        return email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+");
    }
}
