package entities;

/**
 * Classe base abstrata de todo animal atendido na clínica.
 *
 * Guarda o que todo animal tem — nome, idade e cor — e cuida do som: cada
 * espécie tem o seu, e um animal pode receber um som diferente do da espécie.
 *
 * É abstrata porque não existe "animal genérico" na sala de espera: só cachorro,
 * gato, cavalo ou coelho. As duas partes que mudam de espécie para espécie —
 * o nome da espécie e o som — são abstratas, e cada subclasse responde as suas.
 */
public abstract class Animal {

    private String name;
    private int age;
    private String color;

    // Som próprio deste animal. Fica nulo enquanto ele usa o som da espécie,
    // e passa a valer quando setSound() é chamado.
    private String sound;

    protected Animal(String name, int age, String color) {
        this.name = name;
        setAge(age);
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    // Encapsulamento: o setter valida a entrada, então a idade nunca fica
    // negativa, seja pelo construtor ou por uma alteração posterior.
    public void setAge(int age) {
        this.age = Math.max(age, 0);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Nome da espécie, exibido nas listagens.
     */
    public abstract String species();

    /**
     * Som padrão da espécie. É protegido porque só interessa aqui dentro: quem
     * está de fora pergunta o som pelo getSound(), sem saber se ele veio da
     * espécie ou foi trocado.
     */
    protected abstract String speciesSound();

    /**
     * Troca o som deste animal, sem mexer na espécie: um cachorro rouco pode
     * ganhar outro som, e os demais cachorros continuam latindo. Passar null
     * devolve o animal ao som padrão da espécie.
     */
    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getSound() {
        return sound == null ? speciesSound() : sound;
    }

    /**
     * Emite o som do animal. O método é um só, mas o som sai diferente para
     * cada espécie — é o polimorfismo: quem chama não pergunta o tipo do animal.
     */
    public void emitSound() {
        System.out.println(getName() + " faz: " + getSound());
    }

    public void description() {
        System.out.println(
                species() + ": " + getName() +
                " | Idade: " + getAge() + " ano(s)" +
                " | Cor: " + getColor()
        );
    }
}
