package interfaces;

/**
 * Contrato de empréstimo: "eu posso ser emprestado e devolvido".
 *
 * Só Book implementa. Está separado de Describable porque é uma capacidade
 * diferente: um empréstimo sabe se descrever, mas não pode ser emprestado.
 */
public interface Borrowable {

    boolean isAvailable();
    void borrow();
    void giveBack();
}
