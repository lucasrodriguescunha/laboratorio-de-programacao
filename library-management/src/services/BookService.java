package services;

import entities.book.Book;
import exceptions.BookNotFoundException;
import exceptions.BookUnavailableException;
import exceptions.LibraryException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gerenciamento de Livros — funcionalidade do enunciado.
 *
 * Funcionalidades implementadas:
 *   add(Book)                          incluir livro no acervo
 *   update(código, título, autor)      editar livro existente
 *   remove(código)                     remover livro do acervo
 *   list()                             listar todos os livros
 *   findByCode(código)                 localizar um livro pelo código
 *
 * A entidade Book cuida do próprio estado; este serviço cuida da coleção e das
 * regras que dependem dela, como não aceitar dois livros com o mesmo código.
 */
public class BookService {

    private final List<Book> books = new ArrayList<>();

    // O código identifica o livro nas demais operações, então precisa ser único.
    public void add(Book book) {
        for (Book existing : books) {
            if (existing.getCode().equalsIgnoreCase(book.getCode())) {
                throw new LibraryException(
                        "Já existe um livro cadastrado com o código " + book.getCode() + "."
                );
            }
        }
        books.add(book);
    }

    public void update(String code, String title, String author) {
        Book book = findByCode(code);
        book.setTitle(title);
        book.setAuthor(author);
    }

    // Regra de negócio: livro emprestado não sai do acervo, senão o empréstimo
    // em aberto passaria a apontar para um livro que não existe mais.
    public void remove(String code) {
        Book book = findByCode(code);
        if (!book.isAvailable()) {
            throw new BookUnavailableException(
                    "O livro \"" + book.getTitle() + "\" está emprestado e não pode ser removido."
            );
        }
        books.remove(book);
    }

    // Encapsulamento: devolve uma view somente leitura, para que a lista interna
    // só possa ser alterada por add() e remove().
    public List<Book> list() {
        return Collections.unmodifiableList(books);
    }

    public Book findByCode(String code) {
        for (Book book : books) {
            if (book.getCode().equalsIgnoreCase(code)) {
                return book;
            }
        }
        throw new BookNotFoundException("Nenhum livro encontrado com o código " + code + ".");
    }
}
