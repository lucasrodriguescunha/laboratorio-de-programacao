package exceptions;

/**
 * Operação tentada em uma conta encerrada (tipo 4).
 *
 * O enunciado pede que creditar e debitar só funcionem com a conta aberta; a
 * verificação fica na entidade, e esta exceção é a resposta quando ela falha.
 */
public class AccountClosedException extends BankException {

    public AccountClosedException(String message) {
        super(message);
    }
}
