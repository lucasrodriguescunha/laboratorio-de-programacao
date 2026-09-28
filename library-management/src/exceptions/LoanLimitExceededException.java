package exceptions;

public class LoanLimitExceededException extends LibraryException {

    public LoanLimitExceededException(String message) {
        super(message);
    }
}
