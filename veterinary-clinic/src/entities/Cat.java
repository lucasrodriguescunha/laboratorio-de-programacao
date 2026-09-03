package entities;

public class Cat extends Animal {

    public Cat(String name, int age, String color) {
        super(name, age, color);
    }

    @Override
    public String species() {
        return "Gato";
    }

    @Override
    protected String speciesSound() {
        return "Miau!";
    }
}
