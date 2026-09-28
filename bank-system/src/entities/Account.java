package entities;

import exceptions.AccountClosedException;
import exceptions.BankException;
import exceptions.NegativeBalanceException;

import java.util.Locale;

public class Account {

    public static final int CHECKING = 1;
    public static final int SAVINGS = 2;
    public static final int JOINT = 3;
    public static final int CLOSED = 4;

    private final int number;
    private final int branchNumber;
    private final String branchName;
    private int type;
    private double balance;

    public Account() {
        this(0, 0, "Não informada", CHECKING);
    }

    public Account(int number, int branchNumber, String branchName, int type) {
        if (type < CHECKING || type > CLOSED) {
            throw new BankException("Tipo de conta inválido: " + type + ". Use 1, 2, 3 ou 4.");
        }
        this.number = number;
        this.branchNumber = branchNumber;
        this.branchName = branchName;
        this.type = type;
        this.balance = 0D;
    }

    public int getNumber() {
        return number;
    }

    public int getBranchNumber() {
        return branchNumber;
    }

    public String getBranchName() {
        return branchName;
    }

    public int getType() {
        return type;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isClosed() {
        return type == CLOSED;
    }

    public String typeName() {
        switch (type) {
            case CHECKING:
                return "Conta corrente";
            case SAVINGS:
                return "Poupança";
            case JOINT:
                return "Conta conjunta";
            case CLOSED:
                return "Conta encerrada";
            default:
                return "Tipo desconhecido";
        }
    }

    public void credit(double amount) {
        checkOpen("creditar");
        checkAmount(amount);
        balance += amount;
    }

    public void debit(double amount) {
        checkOpen("debitar");
        checkAmount(amount);
        balance -= amount;
    }

    public String balanceText() {
        return "Conta " + number + " (" + typeName() + ") | Saldo: " + money(balance);
    }

    public double close() {
        checkOpen("encerrar");
        if (balance < 0) {
            throw new NegativeBalanceException(
                    "A conta " + number + " está com saldo negativo (" + money(balance) +
                    ") e não pode ser encerrada."
            );
        }

        double withdrawal = balance;
        balance = 0D;
        type = CLOSED;
        return withdrawal;
    }

    public String closingText(double withdrawal) {
        return "Conta " + number + " encerrada.\n" +
               "Agência: " + branchNumber + " - " + branchName + "\n" +
               "Tipo: " + type + " - " + typeName() + "\n" +
               "Saldo devolvido: " + money(withdrawal);
    }

    private void checkOpen(String operation) {
        if (isClosed()) {
            throw new AccountClosedException(
                    "A conta " + number + " está encerrada (tipo " + CLOSED + ") e não é possível " +
                    operation + "."
            );
        }
    }

    private void checkAmount(double amount) {
        if (amount <= 0) {
            throw new BankException("O valor informado deve ser maior que zero.");
        }
    }

    private static String money(double value) {
        return String.format(Locale.forLanguageTag("pt-BR"), "R$ %.2f", value);
    }
}
