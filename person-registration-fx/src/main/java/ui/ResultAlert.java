package ui;

import entities.Person;
import javafx.scene.control.Alert;

public class ResultAlert {

    private ResultAlert() {
    }

    public static void show(Person person) {
        String message = String.format(
                "CPF: %s%nNome: %s%nEndereço: %s%nEstado: %s%nCargo: %s",
                person.getCpf(),
                person.getName(),
                person.getAddress(),
                person.getState(),
                person.getRole()
        );

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Mensagem");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
