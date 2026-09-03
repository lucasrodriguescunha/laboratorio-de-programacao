package entities;

/**
 * Coelho. O enunciado pede Cachorro, Gato e Cavalo, e depois cita o coelho na
 * lista do veterinário — então ele também é uma espécie do sistema. Note que
 * acrescentar uma espécie custa só esta classe: nada em Animal, no Veterinarian
 * ou no Main precisou mudar para atendê-la.
 */
public class Rabbit extends Animal {

    public Rabbit(String name, int age, String color) {
        super(name, age, color);
    }

    @Override
    public String species() {
        return "Coelho";
    }

    @Override
    protected String speciesSound() {
        return "Rangido baixinho!";
    }
}
