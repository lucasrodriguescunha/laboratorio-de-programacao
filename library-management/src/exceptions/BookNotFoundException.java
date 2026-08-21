package exceptions;

/**
 * Lançada por BookService quando nenhum livro do acervo tem o código informado
 * — ao editar, remover ou realizar um empréstimo.
 */
public class BookNotFoundException extends LibraryException {

    public BookNotFoundException(String message) {
        super(message);
    }
}
