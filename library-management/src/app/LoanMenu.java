package app;

import entities.loan.Loan;
import services.LoanService;

/**
 * Submenu de empréstimos: realizar, encerrar e listar os empréstimos.
 *
 * Depende só do LoanService, mesmo tratando de livros e membros: é o serviço
 * que conhece a regra completa e localiza os dois pelos identificadores
 * digitados aqui.
 */
public class LoanMenu extends Menu {

    private final LoanService loanService;

    public LoanMenu(LoanService loanService, ConsoleInput input) {
        super(input);
        this.loanService = loanService;
    }

    @Override
    protected String title() {
        return "----- EMPRÉSTIMOS -----";
    }

    @Override
    protected String[] options() {
        return new String[]{
                "Realizar empréstimo",
                "Encerrar empréstimo",
                "Listar empréstimos",
                "Listar empréstimos de um membro"
        };
    }

    @Override
    protected void execute(int option) {
        switch (option) {
            case 1:
                create();
                break;
            case 2:
                close();
                break;
            case 3:
                list();
                break;
            case 4:
                listByMember();
                break;
        }
    }

    private void create() {
        System.out.println();
        String memberId = input.readText("Matrícula do membro: ");
        String bookCode = input.readText("Código do livro: ");
        int days = input.readInt("Prazo (dias): ");

        Loan loan = loanService.create(memberId, bookCode, days);
        System.out.println("Empréstimo realizado com sucesso.");
        System.out.println();
        loan.description();
    }

    private void close() {
        System.out.println();
        int id = input.readInt("Número do empréstimo: ");

        loanService.close(id);
        System.out.println("Empréstimo encerrado com sucesso.");
    }

    private void list() {
        System.out.println();
        System.out.println("Empréstimos registrados:");
        describeAll(loanService.list());
    }

    private void listByMember() {
        System.out.println();
        String memberId = input.readText("Matrícula do membro: ");

        System.out.println();
        System.out.println("Empréstimos do membro:");
        describeAll(loanService.listByMember(memberId));
    }
}
