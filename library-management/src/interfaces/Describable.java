package interfaces;

/**
 * Contrato de exibição: "eu sei imprimir a minha própria descrição".
 *
 * Implementado por Book, Person e Loan. É o que permite ao menu listar livros,
 * membros e empréstimos com o mesmo trecho de código, sem verificar o tipo de
 * cada objeto — cada um responde do seu jeito (polimorfismo).
 */
public interface Describable {

    void description();
}
