package entities.person;

/**
 * Funcionário da biblioteca, com cargo e salário.
 *
 * Existe para mostrar que a hierarquia de Person atende a dois tipos de pessoa
 * com dados e descrição diferentes: o funcionário não toma livros emprestados,
 * então não tem contador de empréstimos como o membro.
 */
public class Employee extends Person {

    private String role;
    private double salary;

    public Employee(String id, String name, String email, String role, double salary) {
        super(id, name, email);
        this.role = role;
        setSalary(salary);
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = Math.max(salary, 0);
    }

    @Override
    public void description() {
        System.out.println(
                "Funcionário: " + getName() + "\n" +
                "Matrícula: " + getId() + "\n" +
                "Email: " + getEmail() + "\n" +
                "Cargo: " + getRole() + "\n" +
                "Salário: R$ " + getSalary()
        );
    }
}
