package exceptions;

/**
 * Encerramento recusado porque a conta está com saldo negativo.
 *
 * É a regra do enunciado: a conta só pode ser encerrada se não estiver devendo
 * ao banco.
 */
public class NegativeBalanceException extends BankException {

    public NegativeBalanceException(String message) {
        super(message);
    }
}
