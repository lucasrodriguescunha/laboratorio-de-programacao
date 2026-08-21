package exceptions;

/**
 * Exceção base de todo erro previsto do domínio da biblioteca.
 *
 * Todas as demais herdam desta, então o menu captura só LibraryException e
 * exibe a mensagem — sem precisar de um catch para cada caso. É o polimorfismo
 * aplicado ao tratamento de erros.
 *
 * Usada diretamente quando o erro não merece uma subclasse própria: código de
 * livro ou matrícula repetidos, empréstimo inexistente ou já encerrado.
 */
public class LibraryException extends RuntimeException {

    public LibraryException(String message) {
        super(message);
    }
}
