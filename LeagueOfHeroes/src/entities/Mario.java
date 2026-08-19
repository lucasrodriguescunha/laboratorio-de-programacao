package entities;

public class Mario extends Hero {

    public Mario(String name, int life, int energy) {
        super(name, life, energy);
    }

    @Override
    public void attack() {
        System.out.println("Mario lança uma bola de fogo!");
    }
}