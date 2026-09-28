import app.MainWindow;
import entities.Ebook;
import entities.Library;
import entities.LibraryException;
import entities.Member;
import entities.PhysicalBook;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.time.Year;

public class Main {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        Library library = new Library();
        loadSampleData(library);

        SwingUtilities.invokeLater(() -> new MainWindow(library).setVisible(true));
    }

    private static void loadSampleData(Library library) {
        try {
            library.addBook(new PhysicalBook("Dom Casmurro", "Machado de Assis", Year.of(1899), "9788535910667"));
            library.addBook(new PhysicalBook("O Cortiço", "Aluísio Azevedo", Year.of(1890), "9788508133024"));
            library.addBook(new Ebook("Introdução à Programação", "Ana Ribeiro", Year.of(2023), "9786555200010"));
            library.addMember(new Member("Ana Souza", "2024001", "32999990001"));
            library.addMember(new Member("Bruno Lima", "2024002", "3235310000"));
        } catch (LibraryException e) {
            System.err.println("Erro ao carregar dados de exemplo: " + e.getMessage());
        }
    }
}
