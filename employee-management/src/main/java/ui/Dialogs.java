package ui;

import javax.swing.JOptionPane;
import java.awt.Component;

public final class Dialogs {

    private Dialogs() {
    }

    public static void info(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Informação", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void error(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Atenção", JOptionPane.WARNING_MESSAGE);
    }

    public static boolean confirm(Component parent, String message) {
        int option = JOptionPane.showConfirmDialog(parent, message, "Confirmação", JOptionPane.YES_NO_OPTION);
        return option == JOptionPane.YES_OPTION;
    }
}
