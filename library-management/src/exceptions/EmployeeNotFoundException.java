package exceptions;

/**
 * Lançada por EmployeeService quando nenhum funcionário cadastrado tem a
 * matrícula informada — ao editar o cadastro.
 */
public class EmployeeNotFoundException extends LibraryException {

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
