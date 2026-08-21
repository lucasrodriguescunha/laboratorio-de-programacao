package entities.person;

import interfaces.Describable;

/**
 * Classe base abstrata das pessoas do sistema.
 *
 * Reúne o que membro e funcionário têm em comum: identificação, nome e email.
 * É abstrata porque "pessoa genérica" não existe no domínio — quem usa a
 * biblioteca é membro, quem trabalha nela é funcionário.
 */
public abstract class Person implements Describable {

    private final String id;
    private String name;
    private String email;

    public Person(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public abstract void description();
}
