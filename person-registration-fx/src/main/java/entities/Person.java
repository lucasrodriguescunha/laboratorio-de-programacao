package entities;

public class Person {

    private final String cpf;
    private final String name;
    private final String address;
    private final String state;
    private final String role;

    public Person(String cpf, String name, String address, String state, String role) {
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

    public String getState() {
        return state;
    }

    public String getRole() {
        return role;
    }
}
