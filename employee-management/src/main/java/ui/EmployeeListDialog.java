package ui;

import db.DatabaseException;
import entities.Employee;
import services.EmployeeException;
import services.EmployeeService;
import util.MoneyUtils;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class EmployeeListDialog extends JDialog {

    private final EmployeeService service;
    private List<Employee> employees = new ArrayList<>();

    private final JTextField txtSearch = new JTextField(24);
    private final JLabel lblSummary = new JLabel();

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "Nome", "Cargo", "Data de contratação", "Salário"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(tableModel);

    public EmployeeListDialog(Window owner, EmployeeService service) {
        super(owner, "Funcionários Cadastrados", ModalityType.APPLICATION_MODAL);
        this.service = service;

        buildLayout();
        refreshTable();

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(820, 480);
        setLocationRelativeTo(owner);
    }

    private void buildLayout() {
        JButton btnSearch = new JButton("Buscar");
        JButton btnClearSearch = new JButton("Limpar busca");

        btnSearch.addActionListener(e -> refreshTable());
        btnClearSearch.addActionListener(e -> {
            txtSearch.setText("");
            refreshTable();
        });
        txtSearch.addActionListener(e -> refreshTable());

        JPanel search = new JPanel(new FlowLayout(FlowLayout.LEFT));
        search.setBorder(BorderFactory.createTitledBorder("Buscar por nome"));
        search.add(txtSearch);
        search.add(btnSearch);
        search.add(btnClearSearch);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        DefaultTableCellRenderer rightAligned = new DefaultTableCellRenderer();
        rightAligned.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(4).setCellRenderer(rightAligned);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && table.getSelectedRow() >= 0) {
                    editEmployee();
                }
            }
        });

        JButton btnNew = new JButton("Novo");
        JButton btnEdit = new JButton("Editar");
        JButton btnDelete = new JButton("Excluir");
        JButton btnRefresh = new JButton("Atualizar lista");

        btnNew.addActionListener(e -> newEmployee());
        btnEdit.addActionListener(e -> editEmployee());
        btnDelete.addActionListener(e -> deleteEmployee());
        btnRefresh.addActionListener(e -> refreshTable());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnNew);
        buttons.add(btnEdit);
        buttons.add(btnDelete);
        buttons.add(btnRefresh);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.add(lblSummary, BorderLayout.WEST);
        bottom.add(buttons, BorderLayout.EAST);

        JPanel content = new JPanel(new BorderLayout(8, 8));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        content.add(search, BorderLayout.NORTH);
        content.add(new JScrollPane(table), BorderLayout.CENTER);
        content.add(bottom, BorderLayout.SOUTH);
        setContentPane(content);
    }

    private void newEmployee() {
        if (EmployeeFormDialog.open(this, service, null)) {
            refreshTable();
        }
    }

    private void editEmployee() {
        Employee employee = getSelectedEmployee();
        if (employee == null) {
            Dialogs.error(this, "Selecione um funcionário na tabela.");
            return;
        }
        if (EmployeeFormDialog.open(this, service, employee)) {
            refreshTable();
        }
    }

    private void deleteEmployee() {
        Employee employee = getSelectedEmployee();
        if (employee != null && !Dialogs.confirm(this, "Excluir o funcionário \"" + employee.getName() + "\"?")) {
            return;
        }
        try {
            service.delete(employee);
            refreshTable();
            Dialogs.info(this, "Funcionário excluído com sucesso!");
        } catch (EmployeeException | DatabaseException ex) {
            Dialogs.error(this, ex.getMessage());
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        try {
            employees = service.search(txtSearch.getText());
        } catch (DatabaseException ex) {
            employees = new ArrayList<>();
            lblSummary.setText(" Não foi possível carregar os funcionários.");
            Dialogs.error(this, ex.getMessage());
            return;
        }

        BigDecimal payroll = BigDecimal.ZERO;
        for (Employee employee : employees) {
            tableModel.addRow(new Object[]{
                    employee.getId(),
                    employee.getName(),
                    employee.getRole(),
                    employee.getFormattedHireDate(),
                    employee.getFormattedSalary()
            });
            payroll = payroll.add(employee.getSalary());
        }
        lblSummary.setText(" " + employees.size() + " funcionário(s) | Folha salarial: "
                + MoneyUtils.formatCurrency(payroll));
    }

    private Employee getSelectedEmployee() {
        int row = table.getSelectedRow();
        return row < 0 ? null : employees.get(row);
    }
}
