import entities.Animal;
import entities.Cat;
import entities.Dog;
import entities.Horse;
import entities.Rabbit;
import entities.Veterinarian;

public class Main {
    public static void main(String[] args) {

        Veterinarian veterinarian = new Veterinarian("Alex Ramos");

        veterinarian.receive(new Dog("Rex", 3, "Caramelo"));
        veterinarian.receive(new Cat("Mimi", 2, "Branco"));
        veterinarian.receive(new Horse("Trovão", 7, "Preto"));

        Animal bidu = new Dog("Bidu", 9, "Cinza");
        bidu.setSound("Au... au rouco");
        veterinarian.receive(bidu);

        veterinarian.receive(new Rabbit("Pipoca", 1, "Branco"));

        veterinarian.attendAll();
        veterinarian.listVan();
    }
}
