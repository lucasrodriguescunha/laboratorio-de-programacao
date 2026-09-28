package app;

import services.Library;

public class LibraryMenu extends Menu {

    private final BookMenu bookMenu;
    private final MemberMenu memberMenu;
    private final LoanMenu loanMenu;
    private final EmployeeMenu employeeMenu;

    public LibraryMenu(Library library) {
        super(new ConsoleInput());
        this.bookMenu = new BookMenu(library.getBookService(), input);
        this.memberMenu = new MemberMenu(library.getMemberService(), input);
        this.loanMenu = new LoanMenu(library.getLoanService(), input);
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
                loanMenu.start();
                break;
            case 4:
                employeeMenu.start();
                break;
        }
    }
}
