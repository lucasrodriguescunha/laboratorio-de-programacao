package entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Veterinarian {

    private final String name;
    private final List<Animal> animals = new ArrayList<>();

    private final List<Animal> van = new ArrayList<>();

    public Veterinarian(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void receive(Animal animal) {
        animals.add(animal);
    }

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

    public List<Animal> getAnimals() {
        return Collections.unmodifiableList(animals);
    }

    public List<Animal> getVan() {
        return Collections.unmodifiableList(van);
    }
}
