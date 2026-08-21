package entities.loan;

import entities.book.Book;
import entities.person.Member;
import exceptions.LibraryException;
import interfaces.Describable;

import java.time.LocalDate;

/**
 * Empréstimo: associa um livro a um membro, com data de saída, prazo de
 * devolução e situação (ativo ou encerrado).
 *
 * Funcionalidades: encerrar o empréstimo, registrando a data de devolução
 * (close), e informar se está em atraso (isLate).
 *
 * Não herda de Book nem de Person — herança só onde existe relação "é um", e um
 * empréstimo não é um livro nem uma pessoa: ele liga os dois.
 */
public class Loan implements Describable {

    private final int id;
    private final Book book;
    private final Member member;
    private final LocalDate loanDate;
    private final LocalDate dueDate;
    private LocalDate returnDate;
    private LoanStatus loanStatus;

    public Loan(int id, Book book, Member member, LocalDate loanDate, LocalDate dueDate) {
        this.id = id;
        this.book = book;
        this.member = member;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = null;
        this.loanStatus = LoanStatus.ACTIVE;
    }

    public int getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public LoanStatus getLoanStatus() {
        return loanStatus;
    }

    // Regra de negócio: só se encerra um empréstimo ativo. Quem chama é o
    // LoanService, que em seguida devolve o livro e baixa o contador do membro.
    public void close(LocalDate returnDate) {
        if (loanStatus == LoanStatus.CLOSED) {
            throw new LibraryException("O empréstimo " + id + " já está encerrado.");
        }
        this.returnDate = returnDate;
        this.loanStatus = LoanStatus.CLOSED;
    }

    // Empréstimo encerrado compara a data de devolução com o prazo; empréstimo
    // ainda ativo compara com a data de hoje.
    public boolean isLate() {
        LocalDate reference = returnDate != null ? returnDate : LocalDate.now();
        return reference.isAfter(dueDate);
    }

    @Override
    public void description() {
        System.out.println(
                "Empréstimo: " + getId() + "\n" +
                "Livro: " + book.getTitle() + " (" + book.getCode() + ")\n" +
                "Membro: " + member.getName() + " (" + member.getId() + ")\n" +
                "Data do empréstimo: " + getLoanDate() + "\n" +
                "Devolução prevista: " + getDueDate() + "\n" +
                "Devolvido em: " + (getReturnDate() != null ? getReturnDate() : "Não devolvido") + "\n" +
                "Situação: " + (getLoanStatus() == LoanStatus.ACTIVE ? "Ativo" : "Encerrado") +
                (isLate() ? " (em atraso)" : "")
        );
    }
}
