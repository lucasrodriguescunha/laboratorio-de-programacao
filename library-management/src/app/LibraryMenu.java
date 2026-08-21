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
import java.util.Scanner;

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
    private final Scanner sc;

    public LibraryMenu(Library library) {
        this.library = library;
        this.sc = new Scanner(System.in);
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
            option = readInt("Escolha uma opção: ");

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
            option = readInt("Escolha uma opção: ");

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
            option = readInt("Escolha uma opção: ");

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
            option = readInt("Escolha uma opção: ");

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
            option = readInt("Escolha uma opção: ");

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
        int type = readInt("Tipo do livro: ");

        if (type != 1 && type != 2) {
            System.out.println("Tipo inválido.");
            return;
        }

        String code = readText("Código: ");
        String title = readText("Título: ");
        String author = readText("Autor: ");
        int numberOfPages = readInt("Páginas: ");

        Book book;
        if (type == 1) {
            double weight = readDouble("Peso (g): ");
            book = new PhysicalBook(code, title, author, numberOfPages, weight);
        } else {
            double fileSize = readDouble("Tamanho do arquivo (MB): ");
            book = new Ebook(code, title, author, numberOfPages, fileSize);
        }

        library.getBookService().add(book);
        System.out.println("Livro incluído com sucesso.");
    }

    private void updateBook() {
        System.out.println();
        String code = readText("Código do livro: ");
        String title = readText("Novo título: ");
        String author = readText("Novo autor: ");

        library.getBookService().update(code, title, author);
        System.out.println("Livro atualizado com sucesso.");
    }

    private void removeBook() {
        System.out.println();
        String code = readText("Código do livro: ");

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
        String id = readText("Matrícula: ");
        String name = readText("Nome: ");
        String email = readText("Email: ");

        library.getMemberService().register(new Member(id, name, email));
        System.out.println("Membro cadastrado com sucesso.");
    }

    private void updateMember() {
        System.out.println();
        String id = readText("Matrícula do membro: ");
        String name = readText("Novo nome: ");
        String email = readText("Novo email: ");

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
        String id = readText("Matrícula: ");
        String name = readText("Nome: ");
        String email = readText("Email: ");
        String role = readText("Cargo: ");
        double salary = readDouble("Salário: ");

        library.getEmployeeService().register(new Employee(id, name, email, role, salary));
        System.out.println("Funcionário cadastrado com sucesso.");
    }

    private void updateEmployee() {
        System.out.println();
        String id = readText("Matrícula do funcionário: ");
        String name = readText("Novo nome: ");
        String email = readText("Novo email: ");
        String role = readText("Novo cargo: ");
        double salary = readDouble("Novo salário: ");

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
        String memberId = readText("Matrícula do membro: ");
        String bookCode = readText("Código do livro: ");
        int days = readInt("Prazo (dias): ");

        Loan loan = library.getLoanService().create(memberId, bookCode, days);
        System.out.println("Empréstimo realizado com sucesso.");
        System.out.println();
        loan.description();
    }

    private void closeLoan() {
        System.out.println();
        int id = readInt("Número do empréstimo: ");

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
        String memberId = readText("Matrícula do membro: ");

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

    private String readText(String label) {
        System.out.print(label);
        return sc.nextLine().trim();
    }

    // A leitura usa só nextLine(): misturar nextInt() com nextLine() deixaria a
    // quebra de linha no buffer e pularia a próxima pergunta.
    private int readInt(String label) {
        while (true) {
            try {
                return Integer.parseInt(readText(label));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    // Aceita vírgula como separador decimal, o formato que o usuário digita.
    private double readDouble(String label) {
        while (true) {
            try {
                return Double.parseDouble(readText(label).replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
}
