package app;

import entities.person.Employee;
import services.EmployeeService;

/**
 * Submenu de funcionários: cadastrar, editar e listar quem trabalha na
 * biblioteca.
 */
public class EmployeeMenu extends Menu {

    private final EmployeeService employeeService;

    public EmployeeMenu(EmployeeService employeeService, ConsoleInput input) {
        super(input);
        this.employeeService = employeeService;
    }

    @Override
    protected String title() {
        return "----- FUNCIONÁRIOS -----";
    }

    @Override
    protected String[] options() {
        return new String[]{
                "Cadastrar funcionário",
                "Editar funcionário",
                "Listar funcionários"
        };
    }

    @Override
    protected void execute(int option) {
        switch (option) {
            case 1:
                register();
                break;
            case 2:
                update();
                break;
            case 3:
                list();
                break;
        }
    }

    private void register() {
        System.out.println();
        String id = input.readText("Matrícula: ");
        String name = input.readText("Nome: ");
        String email = input.readText("Email: ");
        String role = input.readText("Cargo: ");
        double salary = input.readDouble("Salário: ");

        employeeService.register(new Employee(id, name, email, role, salary));
        System.out.println("Funcionário cadastrado com sucesso.");
    }

    private void update() {
        System.out.println();
        String id = input.readText("Matrícula do funcionário: ");
        String name = input.readText("Novo nome: ");
        String email = input.readText("Novo email: ");
        String role = input.readText("Novo cargo: ");
        double salary = input.readDouble("Novo salário: ");

        employeeService.update(id, name, email, role, salary);
        System.out.println("Funcionário atualizado com sucesso.");
    }

    private void list() {
        System.out.println();
        System.out.println("Funcionários cadastrados:");
        describeAll(employeeService.list());
    }
}
