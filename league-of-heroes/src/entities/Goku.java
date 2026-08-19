package entities;

public class Goku extends Hero {

    public Goku(String name, int life, int energy) {
        super(name, life, energy);
    }

    @Override
    public void attack() {
        System.out.println("Goku lança um Kamehameha!");
    }
}