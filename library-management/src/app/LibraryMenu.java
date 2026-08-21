package app;

import services.Library;

/**
 * Menu principal: o ponto de entrada da interface com o usuário.
 *
 * Não executa nenhuma funcionalidade por conta própria — exibe os grupos do
 * enunciado e encaminha para o submenu de cada um. É também o único ponto que
 * conversa com a fachada Library: cada submenu recebe daqui apenas o serviço
 * de que precisa.
 */
public class LibraryMenu extends Menu {

    private final BookMenu bookMenu;
    private final MemberMenu memberMenu;
    private final LoanMenu loanMenu;
    private final EmployeeMenu employeeMenu;

    public LibraryMenu(Library library) {
        // O ConsoleInput criado aqui é repassado aos submenus, para que todos
        // leiam do mesmo Scanner.
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
