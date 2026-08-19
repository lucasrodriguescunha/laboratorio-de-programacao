package entities;

public class Sonic extends Hero {

    public Sonic(String name, int life, int energy) {
        super(name, life, energy);
    }

    @Override
    public void attack() {
        System.out.println("Sonic ataca girando em alta velocidade!");
    }
}