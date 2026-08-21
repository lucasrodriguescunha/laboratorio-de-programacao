package exceptions;

/**
 * Lançada por LoanService quando o membro já atingiu o número máximo de
 * empréstimos ativos definido em Member.MAX_ACTIVE_LOANS.
 */
public class LoanLimitExceededException extends LibraryException {

    public LoanLimitExceededException(String message) {
        super(message);
    }
}
