package exceptions;

public class AccountClosedException extends BankException {

    public AccountClosedException(String message) {
        super(message);
    }
}
