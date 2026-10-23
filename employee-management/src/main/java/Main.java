import app.MainWindow;
import services.EmployeeService;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        EmployeeService service = new EmployeeService();

        SwingUtilities.invokeLater(() -> new MainWindow(service).setVisible(true));
    }
}
