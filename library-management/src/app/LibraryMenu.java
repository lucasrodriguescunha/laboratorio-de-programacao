package app;

import entities.book.Book;
import entities.book.Ebook;
import entities.book.PhysicalBook;
import entities.loan.Loan;
import entities.person.Employee;
import entities.person.Member;
import exceptions.LibraryException;
import interfaces.Describable;
import services.Library;

import java.util.List;

/**
 * Menu de console: a interface com o usuário do sistema.
 *
 * Organiza as funcionalidades em submenus, um por grupo do enunciado:
 *   bookMenu()      incluir, editar, remover e listar livros
 *   memberMenu()    cadastrar, editar e listar membros
 *   loanMenu()      realizar, encerrar e listar empréstimos
 *   employeeMenu()  cadastrar, editar e listar funcionários
 *
 * É o único lugar do sistema que captura exceções: cada submenu tem um
 * catch (LibraryException) que exibe a mensagem e devolve o usuário ao menu.
 * Como todos os erros do domínio herdam desse tipo, um catch cobre todos os
 * casos — polimorfismo aplicado ao tratamento de erros.
 */
public class LibraryMenu {

    private final Library library;
    private final ConsoleInput input;

    public LibraryMenu(Library library) {
        this.library = library;
        this.input = new ConsoleInput();
    }

    /**
     * Exibe o menu principal e encaminha para os submenus até o usuário sair.
     */
    public void start() {
        int option;
        do {
            System.out.println();
            System.out.println("===== BIBLIOTECA =====");
            System.out.println("1 - Livros");
            System.out.println("2 - Membros");
            System.out.println("3 - Empréstimos");
            System.out.println("4 - Funcionários");
            System.out.println("0 - Sair");
            option = input.readInt("Escolha uma opção: ");

            switch (option) {
                case 1:
                    bookMenu();
                    break;
                case 2:
                    memberMenu();
                    break;
                case 3:
                    loanMenu();
                    break;
                case 4:
                    employeeMenu();
                    break;
                case 0:
                    System.out.println("Até logo!");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (option != 0);
    }

    private void bookMenu() {
        int option;
        do {
            System.out.println();
            System.out.println("----- LIVROS -----");
            System.out.println("1 - Incluir livro");
            System.out.println("2 - Editar livro");
            System.out.println("3 - Remover livro");
            System.out.println("4 - Listar livros");
            System.out.println("0 - Voltar");
            option = input.readInt("Escolha uma opção: ");

            try {
                switch (option) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        updateBook();
                        break;
                    case 3:
                        removeBook();
                        break;
                    case 4:
                        listBooks();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (LibraryException e) {
                // Um catch só para BookNotFoundException, BookUnavailableException
                // e qualquer outro erro do domínio: todos são LibraryException.
                System.out.println("Erro: " + e.getMessage());
            }
        } while (option != 0);
    }

    private void memberMenu() {
        int option;
        do {
            System.out.println();
            System.out.println("----- MEMBROS -----");
            System.out.println("1 - Cadastrar membro");
            System.out.println("2 - Editar membro");
            System.out.println("3 - Listar membros");
            System.out.println("0 - Voltar");
            option = input.readInt("Escolha uma opção: ");

            try {
                switch (option) {
                    case 1:
                        registerMember();
                        break;
                    case 2:
                        updateMember();
                        break;
                    case 3:
                        listMembers();
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

    private void employeeMenu() {
        int option;
        do {
            System.out.println();
            System.out.println("----- FUNCIONÁRIOS -----");
            System.out.println("1 - Cadastrar funcionário");
            System.out.println("2 - Editar funcionário");
            System.out.println("3 - Listar funcionários");
            System.out.println("0 - Voltar");
            option = input.readInt("Escolha uma opção: ");

            try {
                switch (option) {
                    case 1:
                        registerEmployee();
                        break;
                    case 2:
                        updateEmployee();
                        break;
                    case 3:
                        listEmployees();
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

    // Incluir livro: o tipo escolhido define qual subclasse de Book é criada, e
    // daí em diante o serviço trata as duas da mesma forma.
    private void addBook() {
        System.out.println();
        System.out.println("1 - Livro físico");
        System.out.println("2 - Ebook");
        int type = input.readInt("Tipo do livro: ");

        if (type != 1 && type != 2) {
            System.out.println("Tipo inválido.");
            return;
        }

        String code = input.readText("Código: ");
        String title = input.readText("Título: ");
        String author = input.readText("Autor: ");
        int numberOfPages = input.readInt("Páginas: ");

        Book book;
        if (type == 1) {
            double weight = input.readDouble("Peso (g): ");
            book = new PhysicalBook(code, title, author, numberOfPages, weight);
        } else {
            double fileSize = input.readDouble("Tamanho do arquivo (MB): ");
            book = new Ebook(code, title, author, numberOfPages, fileSize);
        }

        library.getBookService().add(book);
        System.out.println("Livro incluído com sucesso.");
    }

    private void updateBook() {
        System.out.println();
        String code = input.readText("Código do livro: ");
        String title = input.readText("Novo título: ");
        String author = input.readText("Novo autor: ");

        library.getBookService().update(code, title, author);
        System.out.println("Livro atualizado com sucesso.");
    }

    private void removeBook() {
        System.out.println();
        String code = input.readText("Código do livro: ");

        library.getBookService().remove(code);
        System.out.println("Livro removido com sucesso.");
    }

    private void listBooks() {
        System.out.println();
        System.out.println("Livros cadastrados:");
        describeAll(library.getBookService().list());
    }

    private void registerMember() {
        System.out.println();
        String id = input.readText("Matrícula: ");
        String name = input.readText("Nome: ");
        String email = input.readText("Email: ");

        library.getMemberService().register(new Member(id, name, email));
        System.out.println("Membro cadastrado com sucesso.");
    }

    private void updateMember() {
        System.out.println();
        String id = input.readText("Matrícula do membro: ");
        String name = input.readText("Novo nome: ");
        String email = input.readText("Novo email: ");

        library.getMemberService().update(id, name, email);
        System.out.println("Membro atualizado com sucesso.");
    }

    private void listMembers() {
        System.out.println();
        System.out.println("Membros cadastrados:");
        describeAll(library.getMemberService().list());
    }

    private void registerEmployee() {
        System.out.println();
        String id = input.readText("Matrícula: ");
        String name = input.readText("Nome: ");
        String email = input.readText("Email: ");
        String role = input.readText("Cargo: ");
        double salary = input.readDouble("Salário: ");

        library.getEmployeeService().register(new Employee(id, name, email, role, salary));
        System.out.println("Funcionário cadastrado com sucesso.");
    }

    private void updateEmployee() {
        System.out.println();
        String id = input.readText("Matrícula do funcionário: ");
        String name = input.readText("Novo nome: ");
        String email = input.readText("Novo email: ");
        String role = input.readText("Novo cargo: ");
        double salary = input.readDouble("Novo salário: ");

        library.getEmployeeService().update(id, name, email, role, salary);
        System.out.println("Funcionário atualizado com sucesso.");
    }

    private void listEmployees() {
        System.out.println();
        System.out.println("Funcionários cadastrados:");
        describeAll(library.getEmployeeService().list());
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

    /**
     * Lista qualquer coleção de objetos que saibam se descrever. As três
     * listagens do sistema — livros, membros e empréstimos — passam por aqui,
     * sem nenhum teste de tipo: cada objeto imprime a si mesmo.
     */
    private void describeAll(List<? extends Describable> items) {
        if (items.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
            return;
        }
        for (Describable item : items) {
            System.out.println();
            item.description();
        }
    }
}
