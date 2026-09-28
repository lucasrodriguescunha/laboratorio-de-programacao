package app;

import entities.Library;
import ui.LoansDialog;
import ui.BooksDialog;
import ui.MembersDialog;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

public class MainWindow extends JFrame {

    private final Library library;

    public MainWindow(Library library) {
        super("Sistema de Biblioteca");
        this.library = library;

        buildLayout();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 360);
        setLocationRelativeTo(null);
    }

    private void buildLayout() {
        JLabel title = new JLabel("Sistema de Biblioteca", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        JLabel subtitle = new JLabel("Escolha uma opção", SwingConstants.CENTER);

        JPanel header = new JPanel(new GridLayout(2, 1, 0, 4));
        header.add(title);
        header.add(subtitle);

        JButton btnBooks = new JButton("Gerenciamento de Livros");
        JButton btnMembers = new JButton("Gerenciamento de Membros");
        JButton btnLoans = new JButton("Gerenciamento de Empréstimos");
        JButton btnExit = new JButton("Sair");

        btnBooks.addActionListener(e -> new BooksDialog(this, library).setVisible(true));
        btnMembers.addActionListener(e -> new MembersDialog(this, library).setVisible(true));
        btnLoans.addActionListener(e -> new LoansDialog(this, library).setVisible(true));
        btnExit.addActionListener(e -> System.exit(0));

        JPanel buttons = new JPanel(new GridLayout(0, 1, 10, 10));
        buttons.add(btnBooks);
        buttons.add(btnMembers);
        buttons.add(btnLoans);
        buttons.add(btnExit);

        JPanel content = new JPanel(new BorderLayout(10, 20));
        content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        content.add(header, BorderLayout.NORTH);
        content.add(buttons, BorderLayout.CENTER);
        setContentPane(content);
    }
}
