package exceptions;

/**
 * Lançada por MemberService quando nenhum membro cadastrado tem a matrícula
 * informada — ao editar o cadastro ou realizar um empréstimo.
 */
public class MemberNotFoundException extends LibraryException {

    public MemberNotFoundException(String message) {
        super(message);
    }
}
