package ui;

import entities.Person;

import javax.swing.*;
import java.awt.*;

public class ResultDialog {

    private ResultDialog() {
    }

    public static void show(Component owner, Person person) {
        String message = String.format(
                "CPF: %s%nNome: %s%nEndereço: %s%nEstado: %s%nCargo: %s",
                person.getCpf(),
                person.getName(),
                person.getAddress(),
                person.getState() != null ? person.getState().getName() : "",
                person.getRole() != null ? person.getRole().getDescription() : ""
        );

        JOptionPane.showMessageDialog(owner, message, "Mensagem", JOptionPane.INFORMATION_MESSAGE);
    }
}
