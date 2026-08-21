package entities.loan;

/**
 * Situação de um empréstimo: ACTIVE enquanto o livro está com o membro, CLOSED
 * depois da devolução. Enum em vez de String evita valores inválidos.
 */
public enum LoanStatus {
    ACTIVE,
    CLOSED
}
