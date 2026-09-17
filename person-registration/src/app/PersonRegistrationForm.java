package app;

import ui.PersonFormPanel;
import ui.ResultDialog;

import javax.swing.*;

public class PersonRegistrationForm extends JFrame {

    public PersonRegistrationForm() {
        setTitle("Cadastro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 320);
        setLocationRelativeTo(null);
        setResizable(false);

        PersonFormPanel panel = new PersonFormPanel();
        panel.onPrintClick(() -> new ResultDialog(this, panel.collectPerson()).setVisible(true));

        setContentPane(panel);
    }
}
