package app;

import java.util.Scanner;

/**
 * Leitura da entrada do console.
 *
 * Reúne o Scanner e a validação do que o usuário digita, para que o menu cuide
 * apenas de exibir opções e chamar a conta. É a proteção das entradas pedida no
 * enunciado: um texto no lugar de um número vira uma nova pergunta, e não uma
 * exceção que derruba o programa.
 */
public class ConsoleInput {

    private final Scanner sc = new Scanner(System.in);

    public String readText(String label) {
        System.out.print(label);

        // Fim da entrada (Ctrl+Z no Windows, Ctrl+D no Linux): sem esta
        // verificação, o Scanner lançaria NoSuchElementException sem mensagem.
        if (!sc.hasNextLine()) {
            throw new IllegalStateException("a entrada do console foi encerrada.");
        }
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
                System.out.println("Digite um valor válido, por exemplo 150,00.");
            }
        }
    }

    public void close() {
        sc.close();
    }
}
