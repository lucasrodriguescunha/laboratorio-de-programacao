package entities;

public class Horse extends Animal {

    public Horse(String name, int age, String color) {
        super(name, age, color);
    }

    @Override
    public String species() {
        return "Cavalo";
    }

    @Override
    protected String speciesSound() {
        return "Relincho!";
    }
}
