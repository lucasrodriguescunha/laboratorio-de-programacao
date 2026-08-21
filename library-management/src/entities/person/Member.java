package entities.person;

/**
 * Membro da biblioteca: a pessoa que pode tomar livros emprestados.
 *
 * Funcionalidade própria: controlar quantos empréstimos ativos possui, para
 * impedir que ultrapasse o limite definido em MAX_ACTIVE_LOANS.
 */
public class Member extends Person {

    private static final int MAX_ACTIVE_LOANS = 3;
    private int activeLoans;

    public Member(String id, String name, String email) {
        super(id, name, email);
        this.activeLoans = 0;
    }

    public int getActiveLoans() {
        return activeLoans;
    }

    // Consultado por LoanService antes de criar um empréstimo.
    public boolean canBorrow() {
        return activeLoans < MAX_ACTIVE_LOANS;
    }

    public void registerLoan() {
        this.activeLoans++;
    }

    // Math.max protege o contador: nem uma devolução repetida o deixa negativo.
    public void registerReturn() {
        this.activeLoans = Math.max(activeLoans - 1, 0);
    }

    @Override
    public void description() {
        System.out.println(
                "Membro: " + getName() + "\n" +
                "Matrícula: " + getId() + "\n" +
                "Email: " + getEmail() + "\n" +
                "Empréstimos ativos: " + getActiveLoans() + "/" + MAX_ACTIVE_LOANS
        );
    }
}
