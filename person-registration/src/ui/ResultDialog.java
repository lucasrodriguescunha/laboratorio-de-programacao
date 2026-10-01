// Aluno: Lucas Rodrigues Cunha
package ui;

import entities.Person;

import javax.swing.*;
import java.awt.*;

/**
 * Mostra os dados da pessoa cadastrada em uma janela de mensagem.
 *
 * É uma classe utilitária: só tem um método estático, por isso o construtor
 * é privado e ninguém cria objetos dela.
 */
public class ResultDialog {

    private ResultDialog() {
    }

    public static void show(Component owner, Person person) {
        // Monta o texto com um dado por linha (%n é a quebra de linha).
        String message = String.format(
                "CPF: %s%nNome: %s%nEndereço: %s%nEstado: %s%nCargo: %s",
                person.getCpf(),
                person.getName(),
                person.getAddress(),
                person.getState() != null ? person.getState().getName() : "",
                person.getRole() != null ? person.getRole().getDescription() : ""
        );

        // O owner é a janela principal: a mensagem abre centralizada sobre ela.
        JOptionPane.showMessageDialog(owner, message, "Mensagem", JOptionPane.INFORMATION_MESSAGE);
    }
}
