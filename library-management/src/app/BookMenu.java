package app;

import entities.book.Book;
import entities.book.Ebook;
import entities.book.PhysicalBook;
import services.BookService;

/**
 * Submenu de livros: incluir, editar, remover e listar o acervo.
 *
 * Recebe apenas o BookService, e não a biblioteca inteira: é o único serviço
 * de que precisa para atender as suas opções.
 */
public class BookMenu extends Menu {

    private final BookService bookService;

    public BookMenu(BookService bookService, ConsoleInput input) {
        super(input);
        this.bookService = bookService;
    }

    @Override
    protected String title() {
        return "----- LIVROS -----";
    }

    @Override
    protected String[] options() {
        return new String[]{
                "Incluir livro",
                "Editar livro",
                "Remover livro",
                "Listar livros"
        };
    }

    @Override
    protected void execute(int option) {
        switch (option) {
            case 1:
                add();
                break;
            case 2:
                update();
                break;
            case 3:
                remove();
                break;
            case 4:
                list();
                break;
        }
    }

    // Incluir livro: o tipo escolhido define qual subclasse de Book é criada, e
    // daí em diante o serviço trata as duas da mesma forma.
    private void add() {
        System.out.println();
        System.out.println("1 - Livro físico");
        System.out.println("2 - Ebook");
        int type = input.readInt("Tipo do livro: ");

        if (type != 1 && type != 2) {
            System.out.println("Tipo inválido.");
            return;
        }

        String code = input.readText("Código: ");
        String title = input.readText("Título: ");
        String author = input.readText("Autor: ");
        int numberOfPages = input.readInt("Páginas: ");

        Book book;
        if (type == 1) {
            double weight = input.readDouble("Peso (g): ");
            book = new PhysicalBook(code, title, author, numberOfPages, weight);
        } else {
            double fileSize = input.readDouble("Tamanho do arquivo (MB): ");
            book = new Ebook(code, title, author, numberOfPages, fileSize);
        }

        bookService.add(book);
        System.out.println("Livro incluído com sucesso.");
    }

    private void update() {
        System.out.println();
        String code = input.readText("Código do livro: ");
        String title = input.readText("Novo título: ");
        String author = input.readText("Novo autor: ");

        bookService.update(code, title, author);
        System.out.println("Livro atualizado com sucesso.");
    }

    private void remove() {
        System.out.println();
        String code = input.readText("Código do livro: ");

        bookService.remove(code);
        System.out.println("Livro removido com sucesso.");
    }

    private void list() {
        System.out.println();
        System.out.println("Livros cadastrados:");
        describeAll(bookService.list());
    }
}
