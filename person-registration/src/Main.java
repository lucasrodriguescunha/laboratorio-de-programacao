import app.PersonRegistrationForm;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PersonRegistrationForm form = new PersonRegistrationForm();
            form.setVisible(true);
        });
    }
}
