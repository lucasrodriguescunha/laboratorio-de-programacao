package entities;

public class Client {

    private final String cpf;
    private final String name;
    private final String phone;
    private final State state;

    public Client(String cpf, String name, String phone, State state) {
        this.cpf = cpf;
        this.name = name;
        this.phone = phone;
        this.state = state;
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public State getState() {
        return state;
    }
}
