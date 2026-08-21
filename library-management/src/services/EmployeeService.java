package services;

import entities.person.Employee;
import exceptions.EmployeeNotFoundException;
import exceptions.LibraryException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gerenciamento de Funcionários.
 *
 * Funcionalidades implementadas:
 *   register(Employee)                              cadastrar funcionário
 *   update(matrícula, nome, email, cargo, salário)  editar funcionário existente
 *   list()                                          listar todos os funcionários
 *   findById(matrícula)                             localizar pela matrícula
 *
 * Mesmas regras do cadastro de membros: a matrícula é única e identifica o
 * funcionário nas demais operações. O funcionário não toma livros emprestados,
 * então LoanService não conhece este serviço.
 */
public class EmployeeService {

    private final List<Employee> employees = new ArrayList<>();

    public void register(Employee employee) {
        for (Employee existing : employees) {
            if (existing.getId().equalsIgnoreCase(employee.getId())) {
                throw new LibraryException(
                        "Já existe um funcionário cadastrado com a matrícula " + employee.getId() + "."
                );
            }
        }
        employees.add(employee);
    }

    public void update(String id, String name, String email, String role, double salary) {
        Employee employee = findById(id);
        employee.setName(name);
        employee.setEmail(email);
        employee.setRole(role);
        employee.setSalary(salary);
    }

    // Somente leitura: o cadastro só muda por register().
    public List<Employee> list() {
        return Collections.unmodifiableList(employees);
    }

    public Employee findById(String id) {
        for (Employee employee : employees) {
            if (employee.getId().equalsIgnoreCase(id)) {
                return employee;
            }
        }
        throw new EmployeeNotFoundException("Nenhum funcionário encontrado com a matrícula " + id + ".");
    }
}
