package services;

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
