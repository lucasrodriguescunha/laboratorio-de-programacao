import app.AccountMenu;
import app.ConsoleInput;
import entities.Account;

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
