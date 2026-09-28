package entities;

public abstract class Animal {

    private String name;
    private int age;
    private String color;

    private String sound;

    protected Animal(String name, int age, String color) {
        this.name = name;
        setAge(age);
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = Math.max(age, 0);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public abstract String species();

    protected abstract String speciesSound();

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getSound() {
        return sound == null ? speciesSound() : sound;
    }

    public void emitSound() {
        System.out.println(getName() + " faz: " + getSound());
    }

    public void description() {
        System.out.println(
                species() + ": " + getName() +
                " | Idade: " + getAge() + " ano(s)" +
                " | Cor: " + getColor()
        );
    }
}
