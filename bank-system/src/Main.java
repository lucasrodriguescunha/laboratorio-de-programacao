import app.AccountMenu;
import app.ConsoleInput;
import entities.Account;

/**
 * Ponto de entrada do sistema bancário.
 *
 * Abre a conta da demonstração — número 1, agência 100 "Sede", conta corrente,
 * saldo 0 — e passa o comando para o menu de console, de onde todas as
 * operações são acessadas.
 *
 * O try/catch daqui é a rede de proteção do enunciado: os erros previstos são
 * tratados no menu, e qualquer falha não prevista vira uma mensagem em vez de
 * uma pilha de exceção na tela. O finally garante o fechamento do Scanner,
 * aconteça o que acontecer.
 */
public class Main {
    public static void main(String[] args) {

        ConsoleInput input = new ConsoleInput();

        try {
            Account account = new Account(1, 100, "Sede", Account.CHECKING);

            System.out.println("Conta aberta.");
            System.out.println(account.balanceText());

            new AccountMenu(account, input).start();
        } catch (RuntimeException e) {
            System.out.println();
            System.out.println("Erro inesperado: " + e.getMessage());
        } finally {
            input.close();
        }
    }
}
