package app;

import java.util.Scanner;

/**
 * Leitura da entrada do console.
 *
 * Reúne o Scanner e a validação do que o usuário digita, para que os menus
 * cuidem apenas de exibir opções e chamar os serviços.
 *
 * Existe uma única instância, compartilhada por todos os menus: dois Scanner
 * sobre System.in disputariam o mesmo buffer e um deles perderia linhas.
 */
public class ConsoleInput {

    private final Scanner sc = new Scanner(System.in);

    public String readText(String label) {
        System.out.print(label);
        return sc.nextLine().trim();
    }

    // A leitura usa só nextLine(): misturar nextInt() com nextLine() deixaria a
    // quebra de linha no buffer e pularia a próxima pergunta.
    public int readInt(String label) {
        while (true) {
            try {
                return Integer.parseInt(readText(label));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    // Aceita vírgula como separador decimal, o formato que o usuário digita.
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
