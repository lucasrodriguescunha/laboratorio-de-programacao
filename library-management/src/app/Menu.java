package app;

import exceptions.LibraryException;
import interfaces.Describable;

import java.util.List;

public abstract class Menu {

    protected final ConsoleInput input;

    protected Menu(ConsoleInput input) {
        this.input = input;
    }

    protected abstract String title();

    protected abstract String[] options();

    protected abstract void execute(int option);

    protected String exitLabel() {
        return "Voltar";
    }

    protected void onExit() {
    }

    public void start() {
        int option;
        do {
            show();
            option = input.readInt("Escolha uma opção: ");

            if (option == 0) {
                onExit();
            } else if (option < 0 || option > options().length) {
                System.out.println("Opção inválida.");
            } else {
                try {
                    execute(option);
                } catch (LibraryException e) {
                    System.out.println("Erro: " + e.getMessage());
                }
            }
        } while (option != 0);
    }

    private void show() {
        String[] options = options();

        System.out.println();
        System.out.println(title());
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + " - " + options[i]);
        }
        System.out.println("0 - " + exitLabel());
    }

    protected void describeAll(List<? extends Describable> items) {
        if (items.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
            return;
        }
        for (Describable item : items) {
            System.out.println();
            item.description();
        }
    }
}
