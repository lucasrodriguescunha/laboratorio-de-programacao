package ui;

import entities.Person;

import javax.swing.*;
import java.awt.*;

public class ResultDialog extends JDialog {

    public ResultDialog(Frame owner, Person person) {
        super(owner, "Seu dados", true);

        JTextArea textArea = new JTextArea(person.formatted());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        getContentPane().add(new JScrollPane(textArea));
        setSize(360, 260);
        setLocationRelativeTo(owner);
    }
}
