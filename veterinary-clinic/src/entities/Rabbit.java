package entities;

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
