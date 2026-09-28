package app;

import java.util.Scanner;

public class ConsoleInput {

    private final Scanner sc = new Scanner(System.in);

    public String readText(String label) {
        System.out.print(label);

        if (!sc.hasNextLine()) {
            throw new IllegalStateException("a entrada do console foi encerrada.");
        }
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
                System.out.println("Digite um valor válido, por exemplo 150,00.");
            }
        }
    }

    public void close() {
        sc.close();
    }
}
