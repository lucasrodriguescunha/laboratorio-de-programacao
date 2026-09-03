package app;

import entities.Account;
import exceptions.BankException;

/**
 * Menu de console da conta: creditar, debitar, consultar saldo, encerrar e sair.
 *
 * O menu só conversa com o usuário — pergunta a opção, lê os valores e exibe as
 * respostas. Quem decide se a operação é permitida é a conta: as regras estão
 * na entidade, não aqui.
 *
 * É também o único lugar que captura as exceções do domínio: como todas herdam
 * de BankException, um único catch atende a qualquer opção, exibe a mensagem e
 * devolve o usuário ao menu.
 */
public class AccountMenu {

    private final Account account;
    private final ConsoleInput input;

    public AccountMenu(Account account, ConsoleInput input) {
        this.account = account;
        this.input = input;
    }

    /**
     * Exibe o menu e atende as escolhas até o usuário encerrar a conta (4) ou
     * sair (5).
     */
    public void start() {
        boolean running = true;

        while (running) {
            show();
            int option = input.readInt("Escolha uma opção: ");

            try {
                switch (option) {
                    case 1:
                        credit();
                        break;
                    case 2:
                        debit();
                        break;
                    case 3:
                        checkBalance();
                        break;
                    case 4:
                        closeAccount();
                        // Só chega aqui se o encerramento deu certo: se a conta
                        // estiver negativa, a exceção pula esta linha e o menu
                        // continua aberto.
                        running = false;
                        break;
                    case 5:
                        System.out.println();
                        System.out.println("Programa encerrado. A conta continua aberta.");
                        running = false;
                        break;
                    default:
                        System.out.println("Opção inválida. Escolha um número de 1 a 5.");
                }
            } catch (BankException e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    private void show() {
        System.out.println();
        System.out.println("----- BANCO -----");
        System.out.println("1 - Creditar");
        System.out.println("2 - Debitar");
        System.out.println("3 - Consultar saldo");
        System.out.println("4 - Encerrar conta");
        System.out.println("5 - Sair");
    }

    private void credit() {
        double amount = input.readDouble("Valor a creditar: ");

        account.credit(amount);
        System.out.println("Crédito realizado.");
        System.out.println(account.balanceText());
    }

    private void debit() {
        double amount = input.readDouble("Valor a debitar: ");

        account.debit(amount);
        System.out.println("Débito realizado.");
        System.out.println(account.balanceText());
    }

    private void checkBalance() {
        System.out.println(account.balanceText());
    }

    private void closeAccount() {
        double withdrawal = account.close();

        System.out.println();
        System.out.println(account.closingText(withdrawal));
    }
}
