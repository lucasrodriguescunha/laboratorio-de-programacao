package entities;

import java.time.LocalDate;

public class Loan {

    private static int nextId = 1;

    private final int id;
    private final Book book;
    private final Member member;
    private final LocalDate loanDate;
    private LocalDate returnDate;
    private LoanStatus status;

    public Loan(Book book, Member member) {
        this.id = nextId++;
        this.book = book;
        this.member = member;
        this.loanDate = LocalDate.now();
        this.status = LoanStatus.ACTIVE;
        book.setAvailable(false);
    }

    public void finish() {
        this.returnDate = LocalDate.now();
        this.status = LoanStatus.FINISHED;
        book.setAvailable(true);
    }

    public int getId() { return id; }
    public Book getBook() { return book; }
    public Member getMember() { return member; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public LoanStatus getStatus() { return status; }
}
