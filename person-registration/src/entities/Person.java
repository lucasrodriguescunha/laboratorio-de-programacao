// Aluno: Lucas Rodrigues Cunha
package entities;

/**
 * Entidade do modelo: representa a pessoa cadastrada no formulário.
 *
 * É imutável: os atributos são final, recebidos no construtor e lidos
 * apenas pelos getters. Não conhece nada da interface gráfica.
 */
public class Person {

    private final String cpf;
    private final String name;
    private final String address;
    // Estado e cargo são enums, então só aceitam os valores previstos.
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
}
