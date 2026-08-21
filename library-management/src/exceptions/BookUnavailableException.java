package exceptions;

/**
 * Lançada quando se tenta usar um livro que já está emprestado: por Book, ao
 * emprestá-lo de novo, e por BookService, ao tentar removê-lo do acervo.
 */
public class BookUnavailableException extends LibraryException {

    public BookUnavailableException(String message) {
        super(message);
    }
}
