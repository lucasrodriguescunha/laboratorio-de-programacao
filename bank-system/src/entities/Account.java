package entities;

import exceptions.AccountClosedException;
import exceptions.BankException;
import exceptions.NegativeBalanceException;

import java.util.Locale;

/**
 * Conta bancária: a entidade do sistema.
 *
 * Guarda os dados que toda conta do sistema bancário brasileiro tem — número,
 * agência (número e nome), tipo e saldo — e as ações que podem ser feitas com
 * ela: abrir, creditar, debitar, consultar o saldo e encerrar.
 *
 * Encapsulamento: os atributos são privados e o saldo não tem setter. Ele só
 * muda dentro de credit(), debit() e close(), que são os únicos lugares onde as
 * regras do enunciado são aplicadas — assim nenhum código de fora consegue
 * alterar o saldo sem passar por elas.
 */
public class Account {

    /**
     * Tipos de conta do enunciado. São constantes com nome porque `type == 4`
     * espalhado pelo código não diz nada, enquanto `type == CLOSED` diz.
     */
    public static final int CHECKING = 1;
    public static final int SAVINGS = 2;
    public static final int JOINT = 3;
    public static final int CLOSED = 4;

    private final int number;
    private final int branchNumber;
    private final String branchName;
    private int type;
    private double balance;

    /**
     * Construtor padrão. Delega para o construtor completo com valores neutros,
     * para que a conta criada assim já nasça em um estado válido — conta
     * corrente, agência não informada e saldo zerado — em vez de ficar com os
     * campos indefinidos.
     */
    public Account() {
        this(0, 0, "Não informada", CHECKING);
    }

    /**
     * Abrir conta: recebe os dados e insere 0 como saldo, como pede o enunciado.
     * Não existe construtor que receba saldo — toda conta começa zerada, e daí
     * em diante o saldo só muda pelas operações.
     */
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

    /**
     * Nome do tipo da conta, para as mensagens não exibirem só o número.
     */
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

    /**
     * Creditar: adiciona o valor recebido ao saldo, se a conta não estiver
     * encerrada.
     */
    public void credit(double amount) {
        checkOpen("creditar");
        checkAmount(amount);
        balance += amount;
    }

    /**
     * Debitar: retira o valor recebido do saldo, se a conta não estiver
     * encerrada.
     *
     * O débito não compara o valor com o saldo: o enunciado prevê que a conta
     * possa ficar negativa — é o que o cheque especial faz — e é justamente por
     * isso que o encerramento precisa recusar saldo negativo.
     */
    public void debit(double amount) {
        checkOpen("debitar");
        checkAmount(amount);
        balance -= amount;
    }

    /**
     * Consultar saldo: mensagem com a conta e o seu saldo atual.
     */
    public String balanceText() {
        return "Conta " + number + " (" + typeName() + ") | Saldo: " + money(balance);
    }

    /**
     * Encerrar conta: só é permitido com saldo não negativo. Marca o tipo como
     * 4 (conta encerrada), zera o saldo e devolve o valor que estava na conta —
     * o dinheiro que o cliente leva no encerramento.
     */
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

    /**
     * Texto de encerramento: número da conta, tipo e o saldo que foi encerrado.
     * Recebe o valor devolvido por close(), porque a essa altura o saldo da
     * conta já é 0.
     */
    public String closingText(double withdrawal) {
        return "Conta " + number + " encerrada.\n" +
               "Agência: " + branchNumber + " - " + branchName + "\n" +
               "Tipo: " + type + " - " + typeName() + "\n" +
               "Saldo devolvido: " + money(withdrawal);
    }

    // Regra do enunciado: conta encerrada não movimenta. A verificação fica aqui
    // uma única vez, e não repetida em cada opção do menu.
    private void checkOpen(String operation) {
        if (isClosed()) {
            throw new AccountClosedException(
                    "A conta " + number + " está encerrada (tipo " + CLOSED + ") e não é possível " +
                    operation + "."
            );
        }
    }

    // Proteção da entrada: creditar ou debitar 0 não faz nada, e um valor
    // negativo inverteria a operação — debitar -50 viraria um crédito.
    private void checkAmount(double amount) {
        if (amount <= 0) {
            throw new BankException("O valor informado deve ser maior que zero.");
        }
    }

    // O sistema é brasileiro, então o valor é exibido em reais, com vírgula
    // como separador decimal.
    private static String money(double value) {
        return String.format(Locale.forLanguageTag("pt-BR"), "R$ %.2f", value);
    }
}
