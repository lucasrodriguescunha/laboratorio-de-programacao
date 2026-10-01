package ui;

import entities.Person;
import entities.Role;
import entities.State;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class PersonFormController {

    private static final int CPF_DIGITS = 11;

    @FXML
    private TextField cpfField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private ComboBox<String> stateCombo;
    @FXML
    private ComboBox<String> roleCombo;
    @FXML
    private Button printButton;

    @FXML
    private void initialize() {
        ObservableList<String> states = FXCollections.observableArrayList();
        for (State state : State.values()) {
            states.add(state.toString());
        }
        ObservableList<String> roles = FXCollections.observableArrayList();
        for (Role role : Role.values()) {
            roles.add(role.toString());
        }

        stateCombo.setItems(states);
        roleCombo.setItems(roles);
        cpfField.setTextFormatter(createCpfFormatter());

        printButton.setOnAction(event -> {
            if (hasRequiredFields()) {
                ResultAlert.show(collectPerson());
            }
        });
    }

    private Person collectPerson() {
        return new Person(
                cpfField.getText(),
                nameField.getText().trim(),
                addressField.getText().trim(),
                stateCombo.getValue(),
                roleCombo.getValue()
        );
    }

    private boolean hasRequiredFields() {
        StringBuilder errors = new StringBuilder();

        if (digitsOf(cpfField.getText()).length() != CPF_DIGITS) {
            errors.append("- CPF é obrigatório e deve estar completo.\n");
        }
        if (isBlank(nameField.getText())) {
            errors.append("- Nome é obrigatório.\n");
        }
        if (isBlank(addressField.getText())) {
            errors.append("- Endereço é obrigatório.\n");
        }
        if (stateCombo.getValue() == null) {
            errors.append("- Estado é obrigatório.\n");
        }
        if (roleCombo.getValue() == null) {
            errors.append("- Cargo é obrigatório.\n");
        }

        if (errors.length() > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos obrigatórios");
            alert.setHeaderText(null);
            alert.setContentText(errors.toString());
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }

    private TextFormatter<String> createCpfFormatter() {
        return new TextFormatter<>(change -> {
            if (!change.isContentChange()) {
                return change;
            }
            String digits = digitsOf(change.getControlNewText());
            if (digits.length() > CPF_DIGITS) {
                return null;
            }
            String formatted = formatCpf(digits);
            change.setRange(0, change.getControlText().length());
            change.setText(formatted);
            change.selectRange(formatted.length(), formatted.length());
            return change;
        });
    }

    private String digitsOf(String text) {
        return text == null ? "" : text.replaceAll("\\D", "");
    }

    private String formatCpf(String digits) {
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < digits.length(); i++) {
            if (i == 3 || i == 6) {
                formatted.append('.');
            } else if (i == 9) {
                formatted.append('-');
            }
            formatted.append(digits.charAt(i));
        }
        return formatted.toString();
    }
}
