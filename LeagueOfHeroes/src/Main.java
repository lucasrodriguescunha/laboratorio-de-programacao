import entities.Goku;
import entities.Hero;
import entities.Mario;
import entities.Sonic;

public class Main {
    public static void main(String[] args) {

        Hero goku = new Goku(
                "Goku",
                1000000000,
                1000000000
        );

        Hero sonic = new Sonic(
                "Sonic",
                100,
                100
        );

        Hero mario = new Mario(
                "Mário",
                100,
                100
        );

        Hero[] heroes = { goku, sonic, mario };

        for (Hero hero : heroes) {
            hero.introduce();
            hero.attack();
            System.out.println();
        }
    }
}