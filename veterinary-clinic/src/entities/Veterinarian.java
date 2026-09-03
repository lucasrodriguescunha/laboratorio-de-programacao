package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Veterinário: atende a fila de animais e encaminha cada um para a carrocinha.
 *
 * Mantém duas listas de Animal: a fila de atendimento e a carrocinha. As duas
 * são ArrayList<Animal>, e não ArrayList<Dog> ou ArrayList<Cat> — é o que
 * permite guardar cachorro, gato, cavalo e coelho lado a lado e tratá-los da
 * mesma forma.
 *
 * O atendimento é o exemplo de polimorfismo do projeto: attendAll() percorre a
 * lista chamando description() e emitSound() sem nenhum teste de tipo. Cada
 * animal responde do seu jeito, e acrescentar uma espécie nova não muda uma
 * linha deste método.
 */
public class Veterinarian {

    private final String name;
    private final List<Animal> animals = new ArrayList<>();

    // A "carrocinha" do enunciado: para onde vai o animal depois de atendido.
    private final List<Animal> van = new ArrayList<>();

    public Veterinarian(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Recebe um animal para atendimento. O parâmetro é Animal, então aceita
     * qualquer subclasse — inclusive as que ainda não existem.
     */
    public void receive(Animal animal) {
        animals.add(animal);
    }

    /**
     * Atende a fila: apresenta cada animal, pede que ele emita o seu som e o
     * encaminha para a carrocinha.
     */
    public void attendAll() {
        System.out.println("----- ATENDIMENTO -----");
        System.out.println("Responsável: " + name);

        for (Animal animal : animals) {
            System.out.println();
            animal.description();
            animal.emitSound();
            sendToVan(animal);
        }
    }

    public void sendToVan(Animal animal) {
        van.add(animal);
    }

    /**
     * Lista quem está na carrocinha.
     */
    public void listVan() {
        System.out.println();
        System.out.println("----- CARROCINHA -----");

        if (van.isEmpty()) {
            System.out.println("A carrocinha está vazia.");
            return;
        }

        for (Animal animal : van) {
            System.out.println(animal.species() + ": " + animal.getName());
        }
    }

    // Encapsulamento: devolve uma view somente leitura, para que as listas
    // internas só possam ser alteradas por receive() e sendToVan().
    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

    public List<Animal> getVan() {
        return Collections.unmodifiableList(van);
    }
}
