package app;

import entities.loan.Loan;
import exceptions.LibraryException;
import services.Library;

/**
 * Menu principal: o ponto de entrada da interface com o usuário.
 *
 * Não executa nenhuma funcionalidade por conta própria — exibe os grupos do
 * enunciado e encaminha para o submenu de cada um.
 */
public class LibraryMenu extends Menu {

    private final Library library;
    private final BookMenu bookMenu;
    private final MemberMenu memberMenu;
    private final EmployeeMenu employeeMenu;

    public LibraryMenu(Library library) {
        super(new ConsoleInput());
        this.library = library;
        this.bookMenu = new BookMenu(library.getBookService(), input);
        this.memberMenu = new MemberMenu(library.getMemberService(), input);
        this.employeeMenu = new EmployeeMenu(library.getEmployeeService(), input);
    }

    @Override
    protected String title() {
        return "===== BIBLIOTECA =====";
    }

    @Override
    protected String[] options() {
        return new String[]{
                "Livros",
                "Membros",
                "Empréstimos",
                "Funcionários"
        };
    }

    @Override
    protected String exitLabel() {
        return "Sair";
    }

    @Override
    protected void onExit() {
        System.out.println("Até logo!");
    }

    @Override
    protected void execute(int option) {
        switch (option) {
            case 1:
                bookMenu.start();
                break;
            case 2:
                memberMenu.start();
                break;
            case 3:
                loanMenu();
                break;
            case 4:
                employeeMenu.start();
                break;
        }
    }

    private void loanMenu() {
        int option;
        do {
            System.out.println();
            System.out.println("----- EMPRÉSTIMOS -----");
            System.out.println("1 - Realizar empréstimo");
            System.out.println("2 - Encerrar empréstimo");
            System.out.println("3 - Listar empréstimos");
            System.out.println("4 - Listar empréstimos de um membro");
            System.out.println("0 - Voltar");
            option = input.readInt("Escolha uma opção: ");

            try {
                switch (option) {
                    case 1:
                        createLoan();
                        break;
                    case 2:
                        closeLoan();
                        break;
                    case 3:
                        listLoans();
                        break;
                    case 4:
                        listLoansByMember();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (LibraryException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (option != 0);
    }
    private void createLoan() {
        System.out.println();
        String memberId = input.readText("Matrícula do membro: ");
        String bookCode = input.readText("Código do livro: ");
        int days = input.readInt("Prazo (dias): ");

        Loan loan = library.getLoanService().create(memberId, bookCode, days);
        System.out.println("Empréstimo realizado com sucesso.");
        System.out.println();
        loan.description();
    }

    private void closeLoan() {
        System.out.println();
        int id = input.readInt("Número do empréstimo: ");

        library.getLoanService().close(id);
        System.out.println("Empréstimo encerrado com sucesso.");
    }

    private void listLoans() {
        System.out.println();
        System.out.println("Empréstimos registrados:");
        describeAll(library.getLoanService().list());
    }

    private void listLoansByMember() {
        System.out.println();
        String memberId = input.readText("Matrícula do membro: ");

        System.out.println();
        System.out.println("Empréstimos do membro:");
        describeAll(library.getLoanService().listByMember(memberId));
    }
}
