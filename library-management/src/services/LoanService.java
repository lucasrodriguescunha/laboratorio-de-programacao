package services;

import entities.book.Book;
import entities.loan.Loan;
import entities.person.Member;
import exceptions.LibraryException;
import exceptions.LoanLimitExceededException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoanService {

    private final List<Loan> loans = new ArrayList<>();
    private final BookService bookService;
    private final MemberService memberService;
    private int nextId = 1;

    public LoanService(BookService bookService, MemberService memberService) {
        this.bookService = bookService;
        this.memberService = memberService;
    }

    public Loan create(String memberId, String bookCode, int days) {
        if (days <= 0) {
            throw new LibraryException("O prazo do empréstimo deve ser de pelo menos um dia.");
        }

        Member member = memberService.findById(memberId);
        Book book = bookService.findByCode(bookCode);

        if (!member.canBorrow()) {
            throw new LoanLimitExceededException(
                    "O membro " + member.getName() + " já atingiu o limite de empréstimos ativos."
            );
        }

        book.borrow();
        member.registerLoan();

        LocalDate today = LocalDate.now();
        Loan loan = new Loan(nextId, book, member, today, today.plusDays(days));
        loans.add(loan);
        nextId++;

        return loan;
    }

    public void close(int id) {
        Loan loan = findById(id);
        loan.close(LocalDate.now());
        loan.getBook().giveBack();
        loan.getMember().registerReturn();
    }

    public List<Loan> list() {
        return Collections.unmodifiableList(loans);
    }

    public List<Loan> listByMember(String memberId) {
        Member member = memberService.findById(memberId);
        List<Loan> result = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getMember().getId().equals(member.getId())) {
                result.add(loan);
            }
        }
        return result;
    }

    public Loan findById(int id) {
        for (Loan loan : loans) {
            if (loan.getId() == id) {
                return loan;
            }
        }
        throw new LibraryException("Nenhum empréstimo encontrado com o número " + id + ".");
    }
}
