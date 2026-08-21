package app;

import exceptions.LibraryException;
import interfaces.Describable;

import java.util.List;

/**
 * Esqueleto de todo menu de console do sistema.
 *
 * Todos os menus funcionam da mesma forma: exibem um cabeçalho e as opções,
 * leem o número escolhido, executam a opção e repetem até o usuário digitar 0.
 * Esse roteiro fica aqui uma única vez, no método start(); cada subclasse
 * preenche só o que muda — o título, os rótulos das opções e o que cada uma
 * faz. É o padrão Template Method: a classe base define a sequência, as filhas
 * definem os passos.
 *
 * Também é aqui que os erros do domínio são tratados: como todos herdam de
 * LibraryException, um único catch atende a qualquer opção de qualquer menu,
 * exibe a mensagem e devolve o usuário ao menu — polimorfismo aplicado ao
 * tratamento de erros.
 */
public abstract class Menu {

    protected final ConsoleInput input;

    protected Menu(ConsoleInput input) {
        this.input = input;
    }

    /**
     * Cabeçalho exibido acima das opções, por exemplo "----- LIVROS -----".
     */
    protected abstract String title();

    /**
     * Rótulos das opções, na ordem em que aparecem: o primeiro é a opção 1, o
     * segundo é a opção 2, e assim por diante. A numeração é impressa por
     * start(), então ela nunca sai de sincronia com o que execute() espera.
     */
    protected abstract String[] options();

    /**
     * Executa a opção escolhida. Recebe apenas números entre 1 e a quantidade
     * de rótulos de options(), porque start() já barrou os inválidos.
     */
    protected abstract void execute(int option);

    /**
     * Rótulo da opção 0: os submenus voltam, o menu principal sai do sistema.
     */
    protected String exitLabel() {
        return "Voltar";
    }

    /**
     * Executado quando o usuário escolhe 0, antes de o menu se encerrar.
     */
    protected void onExit() {
    }

    /**
     * Exibe o menu e atende as escolhas do usuário até ele digitar 0.
     */
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

    /**
     * Lista qualquer coleção de objetos que saibam se descrever. As listagens
     * do sistema — livros, membros, funcionários e empréstimos — passam por
     * aqui, sem nenhum teste de tipo: cada objeto imprime a si mesmo.
     */
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
