package ui;

import db.DatabaseException;
import entities.Employee;
import services.EmployeeException;
import services.EmployeeService;
import util.DateUtils;
import util.MoneyUtils;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Window;

public class EmployeeFormDialog extends JDialog {

    private final EmployeeService service;
    private final Integer employeeId;
    private boolean saved;

    private final JTextField txtId = new JTextField();
    private final JTextField txtName = new JTextField(24);
    private final JTextField txtRole = new JTextField(24);
    private final JTextField txtHireDate = new JTextField(10);
    private final JTextField txtSalary = new JTextField(12);

    public EmployeeFormDialog(Window owner, EmployeeService service, Employee employee) {
        super(owner, employee == null ? "Cadastrar Funcionário" : "Editar Funcionário", ModalityType.APPLICATION_MODAL);
        this.service = service;
        this.employeeId = employee == null ? null : employee.getId();

        configureInputs();
        buildLayout();
        if (employee != null) {
            fillForm(employee);
        }

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    public static boolean open(Window owner, EmployeeService service, Employee employee) {
        EmployeeFormDialog dialog = new EmployeeFormDialog(owner, service, employee);
        dialog.setVisible(true);
        return dialog.saved;
    }

    private void configureInputs() {
        txtId.setEditable(false);
        txtId.setText("(gerado pelo banco)");
        InputFilters.limit(txtName, EmployeeService.MAX_NAME_LENGTH);
        InputFilters.limit(txtRole, EmployeeService.MAX_ROLE_LENGTH);
        InputFilters.digitMask(txtHireDate, 8, DateUtils::mask);
        InputFilters.digitMask(txtSalary, EmployeeService.MAX_SALARY_DIGITS, MoneyUtils::mask);
        txtHireDate.setToolTipText("Formato dd/mm/aaaa");
        txtSalary.setToolTipText("Digite só os números; os centavos são preenchidos automaticamente");
    }

    private void buildLayout() {
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Dados do funcionário"));
        form.add(new JLabel("ID:"));
        form.add(txtId);
        form.add(new JLabel("Nome (até 100):"));
        form.add(txtName);
        form.add(new JLabel("Cargo (até 50):"));
        form.add(txtRole);
        form.add(new JLabel("Data de contratação (dd/mm/aaaa):"));
        form.add(txtHireDate);
        form.add(new JLabel("Salário (R$):"));
        form.add(txtSalary);

        JButton btnSave = new JButton("Salvar");
        JButton btnCancel = new JButton("Cancelar");

        btnSave.addActionListener(e -> save());
        btnCancel.addActionListener(e -> dispose());
        getRootPane().setDefaultButton(btnSave);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnSave);
        buttons.add(btnCancel);

        JPanel content = new JPanel(new BorderLayout(8, 8));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        content.add(form, BorderLayout.CENTER);
        content.add(buttons, BorderLayout.SOUTH);
        setContentPane(content);
    }

    private void fillForm(Employee employee) {
        txtId.setText(String.valueOf(employee.getId()));
        txtName.setText(employee.getName());
        txtRole.setText(employee.getRole());
        txtHireDate.setText(employee.getFormattedHireDate());
        txtSalary.setText(MoneyUtils.format(employee.getSalary()));
    }

    private void save() {
        Employee employee = new Employee(
                employeeId,
                txtName.getText().trim(),
                txtRole.getText().trim(),
                DateUtils.parse(txtHireDate.getText()),
                MoneyUtils.fromCents(txtSalary.getText()));
        try {
            service.save(employee);
            saved = true;
            Dialogs.info(this, employeeId == null
                    ? "Funcionário cadastrado com sucesso! ID: " + employee.getId()
                    : "Funcionário atualizado com sucesso!");
            dispose();
        } catch (EmployeeException | DatabaseException ex) {
            Dialogs.error(this, ex.getMessage());
        }
    }
}
