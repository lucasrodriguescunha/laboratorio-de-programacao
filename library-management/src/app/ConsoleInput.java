package app;

import java.util.Scanner;

public class ConsoleInput {

    private final Scanner sc = new Scanner(System.in);

    public String readText(String label) {
        System.out.print(label);
        return sc.nextLine().trim();
    }

    public int readInt(String label) {
        while (true) {
            try {
                return Integer.parseInt(readText(label));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    public double readDouble(String label) {
        while (true) {
            try {
                return Double.parseDouble(readText(label).replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }
}
