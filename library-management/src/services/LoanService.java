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

/**
 * Gerenciamento de Empréstimos — funcionalidade do enunciado.
 *
 * Funcionalidades implementadas:
 *   create(matrícula, código, dias)    realizar empréstimo
 *   close(número)                      encerrar empréstimo (devolução)
 *   list()                             listar todos os empréstimos
 *   listByMember(matrícula)            listar os empréstimos de um membro
 *   findById(número)                   localizar um empréstimo pelo número
 *
 * É o único ponto que conhece a regra completa do empréstimo, porque ela
 * envolve dois objetos ao mesmo tempo: o livro precisa estar disponível e o
 * membro precisa estar abaixo do limite.
 */
public class LoanService {

    private final List<Loan> loans = new ArrayList<>();
    private final BookService bookService;
    private final MemberService memberService;
    private int nextId = 1;

    public LoanService(BookService bookService, MemberService memberService) {
        this.bookService = bookService;
        this.memberService = memberService;
    }

    /**
     * Realiza um empréstimo validando, nesta ordem: membro existe, livro
     * existe, membro abaixo do limite e livro disponível. Cada falha lança a
     * exceção correspondente, que o menu captura e exibe.
     */
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

        // borrow() é quem verifica a disponibilidade: se o livro já estiver
        // emprestado, lança BookUnavailableException antes de o contador do
        // membro ser alterado.
        book.borrow();
        member.registerLoan();

        LocalDate today = LocalDate.now();
        Loan loan = new Loan(nextId, book, member, today, today.plusDays(days));
        loans.add(loan);
        nextId++;

        return loan;
    }

    /**
     * Encerra o empréstimo e desfaz seus efeitos, no caminho inverso do
     * create(): marca a devolução, libera o livro e baixa o contador do membro.
     */
    public void close(int id) {
        Loan loan = findById(id);
        loan.close(LocalDate.now());
        loan.getBook().giveBack();
        loan.getMember().registerReturn();
    }

    public List<Loan> list() {
        return Collections.unmodifiableList(loans);
    }

    // findById valida a matrícula antes de filtrar: matrícula inexistente vira
    // erro com mensagem, e não uma lista vazia que parece "nenhum empréstimo".
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
