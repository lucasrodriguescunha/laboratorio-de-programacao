package ui;

import entities.Library;
import entities.LibraryException;
import entities.Member;
import util.PhoneUtils;
import util.TextUtils;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;

public class MembersDialog extends JDialog {

    private final Library library;

    private final JTextField txtId = new JTextField();
    private final JTextField txtName = new JTextField(20);
    private final JTextField txtRegistration = new JTextField(12);
    private final JTextField txtContact = new JTextField(16);

    private final JButton btnToggle = new JButton("Ativar/Inativar");

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "Nome", "Matrícula", "Contato", "Situação"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(tableModel);

    public MembersDialog(Frame owner, Library library) {
        super(owner, "Gerenciamento de Membros", true);
        this.library = library;

        configureInputs();
        buildLayout();
        refreshTable();

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(820, 480);
        setLocationRelativeTo(owner);
    }

    private void configureInputs() {
        txtId.setEditable(false);
        InputFilters.limit(txtName, Library.MAX_TEXT_LENGTH);
        InputFilters.digitsOnly(txtRegistration, Library.MAX_REGISTRATION_LENGTH);
        InputFilters.digitMask(txtContact, 11, PhoneUtils::format);
        txtContact.setToolTipText("Celular: (DD) 9XXXX-XXXX | Fixo: (DD) XXXX-XXXX");
    }

    private void buildLayout() {
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Dados do membro"));
        form.add(new JLabel("ID:"));
        form.add(txtId);
        form.add(new JLabel("Nome (até 150):"));
        form.add(txtName);
        form.add(new JLabel("Matrícula (números, até 20):"));
        form.add(txtRegistration);
        form.add(new JLabel("Contato (telefone):"));
        form.add(txtContact);

        JButton btnAdd = new JButton("Cadastrar membro");
        JButton btnEdit = new JButton("Editar membro");
        JButton btnList = new JButton("Listar membros");
        JButton btnClear = new JButton("Limpar campos");

        btnAdd.addActionListener(e -> addMember());
        btnEdit.addActionListener(e -> editMember());
        btnToggle.addActionListener(e -> toggleStatus());
        btnList.addActionListener(e -> refreshTable());
        btnClear.addActionListener(e -> clearForm());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnAdd);
        buttons.add(btnEdit);
        buttons.add(btnToggle);
        buttons.add(btnList);
        buttons.add(btnClear);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(buttons, BorderLayout.SOUTH);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getColumnModel().getColumn(0).setMaxWidth(50);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                fillFormWithSelected();
            }
        });

        JPanel content = new JPanel(new BorderLayout(8, 8));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        content.add(top, BorderLayout.NORTH);
        content.add(new JScrollPane(table), BorderLayout.CENTER);
        setContentPane(content);
    }

    private void addMember() {
        try {
            Member member = new Member(
                    txtName.getText().trim(),
                    txtRegistration.getText().trim(),
                    TextUtils.onlyDigits(txtContact.getText()));
            library.addMember(member);
            refreshTable();
            clearForm();
            Dialogs.info(this, "Membro cadastrado com sucesso! ID: " + member.getId());
        } catch (LibraryException ex) {
            Dialogs.error(this, ex.getMessage());
        }
    }

    private void editMember() {
        try {
            library.updateMember(getSelectedMember(),
                    txtName.getText().trim(),
                    txtRegistration.getText().trim(),
                    TextUtils.onlyDigits(txtContact.getText()));
            refreshTable();
            clearForm();
            Dialogs.info(this, "Membro atualizado com sucesso!");
        } catch (LibraryException ex) {
            Dialogs.error(this, ex.getMessage());
        }
    }

    private void toggleStatus() {
        Member member = getSelectedMember();
        try {
            library.toggleMemberStatus(member);
            refreshTable();
            clearForm();
            Dialogs.info(this, "Membro \"" + member.getName() + "\" agora está " + member.getStatusLabel() + ".");
        } catch (LibraryException ex) {
            Dialogs.error(this, ex.getMessage());
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Member member : library.getMembers()) {
            tableModel.addRow(new Object[]{
                    member.getId(),
                    member.getName(),
                    member.getRegistration(),
                    member.getFormattedContact(),
                    member.getStatusLabel()
            });
        }
    }

    private Member getSelectedMember() {
        int row = table.getSelectedRow();
        if (row < 0) {
            return null;
        }
        int id = (Integer) tableModel.getValueAt(row, 0);
        return library.findMemberById(id);
    }

    private void fillFormWithSelected() {
        Member member = getSelectedMember();
        if (member == null) {
            return;
        }
        txtId.setText(String.valueOf(member.getId()));
        txtName.setText(member.getName());
        txtRegistration.setText(member.getRegistration());
        txtContact.setText(member.getContact());
        btnToggle.setText(member.isActive() ? "Inativar membro" : "Ativar membro");
    }

    private void clearForm() {
        table.clearSelection();
        txtId.setText("");
        txtName.setText("");
        txtRegistration.setText("");
        txtContact.setText("");
        btnToggle.setText("Ativar/Inativar");
        txtName.requestFocus();
    }
}
