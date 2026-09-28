package entities;

import util.IsbnUtils;
import util.PhoneUtils;
import util.TextUtils;

import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Library {

    public static final int MAX_TEXT_LENGTH = 150;
    public static final int MAX_REGISTRATION_LENGTH = 20;
    public static final int MIN_YEAR = 1450;

    private final List<Book> books = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();
    private final List<Loan> loans = new ArrayList<>();

    public void addBook(Book book) throws LibraryException {
        validateBook(book.getTitle(), book.getAuthor(), book.getYear(), book.getIsbn(), null);
        books.add(book);
    }

    public void updateBook(Book book, String title, String author, Year year, String isbn)
            throws LibraryException {
        if (book == null) {
            throw new LibraryException("Selecione um livro na tabela.");
        }
        validateBook(title, author, year, isbn, book);
        book.setTitle(title);
        book.setAuthor(author);
        book.setYear(year);
        book.setIsbn(isbn);
    }

    public void removeBook(Book book) throws LibraryException {
        if (book == null) {
            throw new LibraryException("Selecione um livro na tabela.");
        }
        if (!book.isAvailable()) {
            throw new LibraryException("Não é possível remover um livro que está emprestado.");
        }
        books.remove(book);
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    public Book findBookById(int id) {
        return books.stream().filter(b -> b.getId() == id).findFirst().orElse(null);
    }

    public List<Book> getAvailableBooks() {
        return books.stream().filter(Book::isAvailable).collect(Collectors.toList());
    }

    private void validateBook(String title, String author, Year year, String isbn, Book ignore)
            throws LibraryException {
        requireText(title, "título");
        requireMaxLength(title, "título", MAX_TEXT_LENGTH);

        requireText(author, "autor");
        requireMaxLength(author, "autor", MAX_TEXT_LENGTH);
        if (!TextUtils.isPersonName(author)) {
            throw new LibraryException("O autor deve conter apenas letras (sem números ou símbolos).");
        }

        if (year == null) {
            throw new LibraryException("Informe o ano com 4 dígitos.");
        }
        Year currentYear = Year.now();
        if (year.isBefore(Year.of(MIN_YEAR)) || year.isAfter(currentYear)) {
            throw new LibraryException("O ano deve estar entre " + MIN_YEAR + " e " + currentYear + ".");
        }

        if (!IsbnUtils.hasThirteenDigits(isbn)) {
            throw new LibraryException("O ISBN deve ter 13 dígitos (ex.: 978-65-1234567-8).");
        }
        if (!IsbnUtils.isBrazilian(isbn)) {
            throw new LibraryException("ISBN brasileiro deve começar com 978-65 ou 978-85.");
        }
        if (!IsbnUtils.hasValidCheckDigit(isbn)) {
            throw new LibraryException("ISBN inválido: o dígito verificador não confere.");
        }
        boolean duplicated = books.stream().anyMatch(b -> b != ignore && b.getIsbn().equals(isbn));
        if (duplicated) {
            throw new LibraryException("Já existe um livro com o ISBN " + IsbnUtils.format(isbn) + ".");
        }
    }

    public void addMember(Member member) throws LibraryException {
        validateMember(member.getName(), member.getRegistration(), member.getContact(), null);
        members.add(member);
    }

    public void updateMember(Member member, String name, String registration, String contact)
            throws LibraryException {
        if (member == null) {
            throw new LibraryException("Selecione um membro na tabela.");
        }
        validateMember(name, registration, contact, member);
        member.setName(name);
        member.setRegistration(registration);
        member.setContact(contact);
    }

    public void toggleMemberStatus(Member member) throws LibraryException {
        if (member == null) {
            throw new LibraryException("Selecione um membro na tabela.");
        }
        if (member.isActive() && hasActiveLoan(member)) {
            throw new LibraryException("Não é possível inativar um membro com empréstimo ativo.");
        }
        member.setActive(!member.isActive());
    }

    public List<Member> getMembers() {
        return Collections.unmodifiableList(members);
    }

    public List<Member> getActiveMembers() {
        return members.stream().filter(Member::isActive).collect(Collectors.toList());
    }

    public Member findMemberById(int id) {
        return members.stream().filter(m -> m.getId() == id).findFirst().orElse(null);
    }

    private boolean hasActiveLoan(Member member) {
        return loans.stream().anyMatch(l -> l.getMember() == member && l.getStatus() == LoanStatus.ACTIVE);
    }

    private void validateMember(String name, String registration, String contact, Member ignore)
            throws LibraryException {
        requireText(name, "nome");
        requireMaxLength(name, "nome", MAX_TEXT_LENGTH);

        requireText(registration, "matrícula");
        if (!registration.matches("\\d{1," + MAX_REGISTRATION_LENGTH + "}")) {
            throw new LibraryException("A matrícula deve ser numérica, com até "
                    + MAX_REGISTRATION_LENGTH + " dígitos.");
        }

        requireText(contact, "contato");
        if (!PhoneUtils.isValid(contact)) {
            throw new LibraryException("Telefone inválido. Use (DD) 9XXXX-XXXX para celular "
                    + "ou (DD) XXXX-XXXX para fixo.");
        }

        boolean duplicated = members.stream()
                .anyMatch(m -> m != ignore && m.getRegistration().equals(registration));
        if (duplicated) {
            throw new LibraryException("Já existe um membro com a matrícula " + registration + ".");
        }
    }

    public Loan lendBook(Book book, Member member) throws LibraryException {
        if (book == null) {
            throw new LibraryException("Selecione um livro disponível.");
        }
        if (member == null) {
            throw new LibraryException("Selecione um membro.");
        }
        if (!member.isActive()) {
            throw new LibraryException("Membro inativo não pode realizar empréstimos.");
        }
        if (!book.isAvailable()) {
            throw new LibraryException("Este livro já está emprestado.");
        }
        Loan loan = new Loan(book, member);
        loans.add(loan);
        return loan;
    }

    public void finishLoan(Loan loan) throws LibraryException {
        if (loan == null) {
            throw new LibraryException("Selecione um empréstimo na tabela.");
        }
        if (loan.getStatus() != LoanStatus.ACTIVE) {
            throw new LibraryException("Este empréstimo já foi encerrado.");
        }
        loan.finish();
    }

    public List<Loan> getLoans() {
        return Collections.unmodifiableList(loans);
    }

    public List<Loan> getLoansByStatus(LoanStatus status) {
        if (status == null) {
            return getLoans();
        }
        return loans.stream().filter(l -> l.getStatus() == status).collect(Collectors.toList());
    }

    private void requireText(String value, String field) throws LibraryException {
        if (value == null || value.isBlank()) {
            throw new LibraryException("Preencha o campo " + field + ".");
        }
    }

    private void requireMaxLength(String value, String field, int max) throws LibraryException {
        if (value.length() > max) {
            throw new LibraryException("O campo " + field + " aceita no máximo " + max + " caracteres.");
        }
    }
}
