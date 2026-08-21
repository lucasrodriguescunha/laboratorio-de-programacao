import app.LibraryMenu;
import entities.book.Ebook;
import entities.book.PhysicalBook;
import entities.person.Member;
import services.Library;

/**
 * Ponto de entrada do sistema de gerenciamento de biblioteca.
 *
 * Cria a biblioteca, cadastra alguns dados de exemplo para a demonstração e
 * abre o menu de console, de onde todas as funcionalidades são acessadas.
 */
public class Main {
    public static void main(String[] args) {

        Library library = new Library();
        loadSampleData(library);

        new LibraryMenu(library).start();
    }

    // Dados de exemplo para a demonstração: um ebook, um livro físico e um
    // membro, para que o menu já abra com o que listar e emprestar. Os
    // funcionários começam vazios e são cadastrados pelo menu.
    private static void loadSampleData(Library library) {

        library.getBookService().add(new Ebook(
                "978-65-00-00077-1",
                "Engenharia de Software Moderna",
                "Marco Tulio Valente",
                395,
                9.9D
        ));

        library.getBookService().add(new PhysicalBook(
                "8543024978",
                "Engenharia de Software Moderna",
                "Marco Tulio Valente",
                395,
                1680D
        ));

        library.getMemberService().register(new Member(
                "1",
                "Lucas Rodrigues Cunha",
                "lucasrodriguescunha@email.com"
        ));
    }
}
