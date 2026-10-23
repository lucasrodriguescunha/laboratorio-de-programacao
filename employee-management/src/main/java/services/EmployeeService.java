package services;

import dao.EmployeeDao;
import db.DatabaseException;
import entities.Employee;
import util.TextUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class EmployeeService {

    public static final int MAX_NAME_LENGTH = 100;
    public static final int MAX_ROLE_LENGTH = 50;
    public static final int MAX_SALARY_DIGITS = 10;
    public static final BigDecimal MAX_SALARY = new BigDecimal("99999999.99");
    public static final LocalDate MIN_HIRE_DATE = LocalDate.of(1900, 1, 1);

    private final EmployeeDao dao = new EmployeeDao();

    public List<Employee> search(String name) throws DatabaseException {
        return dao.findByName(name == null ? "" : name.trim());
    }

    public void save(Employee employee) throws EmployeeException, DatabaseException {
        validate(employee);
        if (employee.getId() == null) {
            dao.insert(employee);
        } else if (!dao.update(employee)) {
            throw new EmployeeException("Funcionário não encontrado. Ele pode ter sido excluído.");
        }
    }

    public void delete(Employee employee) throws EmployeeException, DatabaseException {
        if (employee == null) {
            throw new EmployeeException("Selecione um funcionário na tabela.");
        }
        if (!dao.delete(employee.getId())) {
            throw new EmployeeException("Funcionário não encontrado. Ele pode já ter sido excluído.");
        }
    }

    private void validate(Employee employee) throws EmployeeException {
        String name = employee.getName();
        if (name == null || name.isBlank()) {
            throw new EmployeeException("Informe o nome do funcionário.");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new EmployeeException("O nome deve ter no máximo " + MAX_NAME_LENGTH + " caracteres.");
        }
        if (!TextUtils.isPersonName(name)) {
            throw new EmployeeException("O nome deve conter apenas letras.");
        }

        String role = employee.getRole();
        if (role == null || role.isBlank()) {
            throw new EmployeeException("Informe o cargo do funcionário.");
        }
        if (role.length() > MAX_ROLE_LENGTH) {
            throw new EmployeeException("O cargo deve ter no máximo " + MAX_ROLE_LENGTH + " caracteres.");
        }

        LocalDate hireDate = employee.getHireDate();
        if (hireDate == null) {
            throw new EmployeeException("Informe uma data de contratação válida (dd/mm/aaaa).");
        }
        if (hireDate.isAfter(LocalDate.now())) {
            throw new EmployeeException("A data de contratação não pode estar no futuro.");
        }
        if (hireDate.isBefore(MIN_HIRE_DATE)) {
            throw new EmployeeException("A data de contratação não pode ser anterior a 01/01/1900.");
        }

        BigDecimal salary = employee.getSalary();
        if (salary == null || salary.signum() <= 0) {
            throw new EmployeeException("Informe um salário maior que zero.");
        }
        if (salary.compareTo(MAX_SALARY) > 0) {
            throw new EmployeeException("O salário não pode passar de R$ 99.999.999,99.");
        }
    }
}
