package entities;

public abstract class Hero {

    private String name;
    private int life;
    private int energy;

    public Hero(String name, int life, int energy) {
        this.name = name;
        setLife(life);
        setEnergy(energy);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = Math.max(life, 0);
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = Math.max(energy, 0);
    }

    public void introduce() {
        System.out.println("Olá, eu sou " + getName() + " | Vida: " + getLife() + " | Energia: " + getEnergy());
    }

    public abstract void attack();
}