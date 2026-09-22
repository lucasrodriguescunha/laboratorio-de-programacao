package app;

import entities.Client;
import ui.ClientFormPanel;
import ui.Theme;
import ui.WelcomePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {

    private static final String STUDENT_NAME = "Lucas";
    private static final String WELCOME = "welcome";
    private static final String FORM = "form";

    private final CardLayout cards = new CardLayout();
    private final JPanel content = new JPanel(cards);

    public MainWindow() {
        setTitle("MENU, ITENS DE MENU, FONTES");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        ClientFormPanel form = new ClientFormPanel();
        form.onSaveClick(() -> {
            Client client = form.collectClient();
            JOptionPane.showMessageDialog(this, "Cliente " + client.getName() + " salvo com sucesso!",
                    "Cadastro", JOptionPane.INFORMATION_MESSAGE);
            form.clear();
            showPanel(WELCOME);
        });
        form.onCancelClick(() -> {
            form.clear();
            showPanel(WELCOME);
        });

        content.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
        content.add(new WelcomePanel(), WELCOME);
        content.add(form, FORM);

        setJMenuBar(createMenuBar());
        setContentPane(content);
    }

    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu fileMenu = new JMenu("Arquivo");
        fileMenu.setMnemonic(KeyEvent.VK_A);
        fileMenu.add(createItem("Novo", Theme.DARK_GREEN, e -> showPanel(FORM)));
        fileMenu.add(createItem("Sair", Theme.RED, e -> System.exit(0)));

        JMenu reportMenu = new JMenu("Relatório");
        reportMenu.setMnemonic(KeyEvent.VK_R);
        reportMenu.add(createItem("Cliente", Theme.BLUE,
                e -> JOptionPane.showMessageDialog(this, "Relatório de clientes")));
        reportMenu.add(createItem("Fornecedor", Theme.BLUE,
                e -> JOptionPane.showMessageDialog(this, "Relatório de fornecedores")));

        JMenu aboutMenu = new JMenu("Sobre");
        aboutMenu.setMnemonic(KeyEvent.VK_S);
        aboutMenu.add(createItem("Info", Theme.DARK_GREEN,
                e -> JOptionPane.showMessageDialog(this, "Aluno: " + STUDENT_NAME,
                        "Sobre", JOptionPane.INFORMATION_MESSAGE)));

        bar.add(fileMenu);
        bar.add(reportMenu);
        bar.add(aboutMenu);
        return bar;
    }

    private JMenuItem createItem(String text, Color iconColor, ActionListener action) {
        JMenuItem item = new JMenuItem(text, Theme.dot(iconColor));
        item.addActionListener(action);
        return item;
    }

    private void showPanel(String name) {
        cards.show(content, name);
    }
}
