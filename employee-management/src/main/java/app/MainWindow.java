package app;

import db.ConnectionFactory;
import db.DatabaseException;
import services.EmployeeService;
import ui.Dialogs;
import ui.EmployeeFormDialog;
import ui.EmployeeListDialog;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class MainWindow extends JFrame {

    private static final Color CONNECTED = new Color(0, 128, 0);
    private static final Color DISCONNECTED = new Color(178, 34, 34);

    private final EmployeeService service;
    private final JLabel lblStatus = new JLabel("", SwingConstants.CENTER);

    public MainWindow(EmployeeService service) {
        super("Gerenciamento de Funcionários");
        this.service = service;

        buildLayout();
        checkConnection(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(440, 400);
        setLocationRelativeTo(null);
    }

    private void buildLayout() {
        JLabel title = new JLabel("Gerenciamento de Funcionários", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 22f));
        JLabel subtitle = new JLabel("Escolha uma opção", SwingConstants.CENTER);

        JPanel header = new JPanel(new GridLayout(2, 1, 0, 4));
        header.add(title);
        header.add(subtitle);

        JButton btnNew = new JButton("Cadastrar Funcionário");
        JButton btnList = new JButton("Listar Funcionários");
        JButton btnTest = new JButton("Testar Conexão");
        JButton btnExit = new JButton("Sair");

        btnNew.addActionListener(e -> EmployeeFormDialog.open(this, service, null));
        btnList.addActionListener(e -> new EmployeeListDialog(this, service).setVisible(true));
        btnTest.addActionListener(e -> checkConnection(true));
        btnExit.addActionListener(e -> System.exit(0));

        JPanel buttons = new JPanel(new GridLayout(0, 1, 10, 10));
        buttons.add(btnNew);
        buttons.add(btnList);
        buttons.add(btnTest);
        buttons.add(btnExit);

        JPanel content = new JPanel(new BorderLayout(10, 20));
        content.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        content.add(header, BorderLayout.NORTH);
        content.add(buttons, BorderLayout.CENTER);
        content.add(lblStatus, BorderLayout.SOUTH);
        setContentPane(content);
    }

    private void checkConnection(boolean showDetails) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            DatabaseMetaData meta = conn.getMetaData();
            lblStatus.setText("Banco de dados: conectado");
            lblStatus.setForeground(CONNECTED);
            if (showDetails) {
                Dialogs.info(this, "Conexão realizada com sucesso!\n\n"
                        + "Servidor: " + meta.getDatabaseProductName() + " " + meta.getDatabaseProductVersion() + "\n"
                        + "Driver: " + meta.getDriverName() + " " + meta.getDriverVersion() + "\n"
                        + "URL: " + ConnectionFactory.getUrl() + "\n"
                        + "Usuário: " + meta.getUserName());
            }
        } catch (DatabaseException | SQLException ex) {
            lblStatus.setText("Banco de dados: sem conexão");
            lblStatus.setForeground(DISCONNECTED);
            if (showDetails) {
                Dialogs.error(this, ex.getMessage());
            }
        }
    }
}
