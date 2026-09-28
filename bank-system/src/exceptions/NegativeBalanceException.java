package exceptions;

public class NegativeBalanceException extends BankException {

    public NegativeBalanceException(String message) {
        super(message);
    }
}
