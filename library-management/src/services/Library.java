package services;

/**
 * Fachada da biblioteca: reúne os serviços num único objeto.
 *
 * O menu conversa só com esta classe, e é ela que garante que BookService,
 * MemberService e LoanService compartilhem as mesmas listas — LoanService
 * recebe no construtor exatamente os serviços criados aqui.
 */
public class Library {

    private final BookService bookService;
    private final MemberService memberService;
    private final EmployeeService employeeService;
    private final LoanService loanService;

    public Library() {
        this.bookService = new BookService();
        this.memberService = new MemberService();
        this.employeeService = new EmployeeService();
        this.loanService = new LoanService(bookService, memberService);
    }

    public BookService getBookService() {
        return bookService;
    }

    public MemberService getMemberService() {
        return memberService;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public LoanService getLoanService() {
        return loanService;
    }
}
