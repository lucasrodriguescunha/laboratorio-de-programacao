package entities;

public class Dog extends Animal {

    public Dog(String name, int age, String color) {
        super(name, age, color);
    }

    @Override
    public String species() {
        return "Cachorro";
    }

    @Override
    protected String speciesSound() {
        return "Au au!";
    }
}
