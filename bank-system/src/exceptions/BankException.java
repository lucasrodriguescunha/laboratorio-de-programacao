package exceptions;

/**
 * Exceção base de todo erro previsto do domínio bancário.
 *
 * Todas as demais herdam desta, então o menu captura só BankException e exibe a
 * mensagem — sem precisar de um catch para cada caso. É o polimorfismo aplicado
 * ao tratamento de erros.
 *
 * Usada diretamente quando o erro não merece uma subclasse própria: tipo de
 * conta inválido na abertura e valor de crédito ou débito menor ou igual a zero.
 */
public class BankException extends RuntimeException {

    public BankException(String message) {
        super(message);
    }
}
