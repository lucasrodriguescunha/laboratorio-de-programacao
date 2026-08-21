import app.LibraryMenu;
import app.SampleData;
import services.Library;

/**
 * Ponto de entrada do sistema de gerenciamento de biblioteca.
 *
 * Cria a biblioteca, carrega os dados de exemplo da demonstração e abre o menu
 * de console, de onde todas as funcionalidades são acessadas.
 */
public class Main {
    public static void main(String[] args) {

        Library library = new Library();
        SampleData.load(library);

        new LibraryMenu(library).start();
    }
}
