package entities;

public class Person {

    private final String cpf;
    private final String name;
    private final String address;
    private final State state;
    private final Role role;

    public Person(String cpf, String name, String address, State state, Role role) {
        this.cpf = cpf;
        this.name = name;
        this.address = address;
        this.state = state;
        this.role = role;
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public State getState() {
        return state;
    }

    public Role getRole() {
        return role;
    }

    public String formatted() {
        return "CPF: " + cpf + "\n"
                + "Nome: " + name + "\n"
                + "Endereço: " + address + "\n"
                + "Estado: " + (state != null ? state.getName() : "") + "\n"
                + "Cargo: " + (role != null ? role.getDescription() : "");
    }
}
