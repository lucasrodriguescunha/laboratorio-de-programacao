package ui;

import entities.Book;
import entities.Library;
import entities.LibraryException;
import entities.Loan;
import entities.LoanStatus;
import entities.Member;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LoansDialog extends JDialog {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Library library;

    private final JComboBox<Book> cbBook = new JComboBox<>();
    private final JComboBox<Member> cbMember = new JComboBox<>();
    private final JComboBox<String> cbFilter = new JComboBox<>(new String[]{"Todos", "Ativos", "Finalizados"});

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "Livro", "Membro", "Data do empréstimo", "Data de devolução", "Situação"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(tableModel);

    private List<Loan> displayedLoans = new ArrayList<>();

    public LoansDialog(Frame owner, Library library) {
        super(owner, "Gerenciamento de Empréstimos", true);
        this.library = library;

        buildLayout();
        refreshCombos();
        refreshTable();

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(850, 480);
        setLocationRelativeTo(owner);
    }

    private void buildLayout() {
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Novo empréstimo"));
        form.add(new JLabel("Livro (disponíveis):"));
        form.add(cbBook);
        form.add(new JLabel("Membro (ativos):"));
        form.add(cbMember);

        JButton btnLend = new JButton("Realizar empréstimo");
        JButton btnFinish = new JButton("Encerrar empréstimo selecionado");

        btnLend.addActionListener(e -> lendBook());
        btnFinish.addActionListener(e -> finishLoan());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnLend);
        buttons.add(btnFinish);

        cbFilter.addActionListener(e -> refreshTable());
        JPanel filter = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filter.add(new JLabel("Listar empréstimos:"));
        filter.add(cbFilter);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.NORTH);
        top.add(buttons, BorderLayout.CENTER);
        top.add(filter, BorderLayout.SOUTH);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel content = new JPanel(new BorderLayout(8, 8));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        content.add(top, BorderLayout.NORTH);
        content.add(new JScrollPane(table), BorderLayout.CENTER);
        setContentPane(content);
    }

    private void lendBook() {
        try {
            Book book = (Book) cbBook.getSelectedItem();
            Member member = (Member) cbMember.getSelectedItem();
            library.lendBook(book, member);
            refreshCombos();
            refreshTable();
            Dialogs.info(this, "Empréstimo realizado: \"" + book.getTitle() + "\" para " + member.getName() + ".");
        } catch (LibraryException ex) {
            Dialogs.error(this, ex.getMessage());
        }
    }

    private void finishLoan() {
        try {
            library.finishLoan(getSelectedLoan());
            refreshCombos();
            refreshTable();
            Dialogs.info(this, "Empréstimo encerrado. Livro devolvido ao acervo.");
        } catch (LibraryException ex) {
            Dialogs.error(this, ex.getMessage());
        }
    }

    private void refreshCombos() {
        cbBook.setModel(new DefaultComboBoxModel<>(library.getAvailableBooks().toArray(new Book[0])));
        cbMember.setModel(new DefaultComboBoxModel<>(library.getActiveMembers().toArray(new Member[0])));
    }

    private void refreshTable() {
        LoanStatus status;
        switch (cbFilter.getSelectedIndex()) {
            case 1:
                status = LoanStatus.ACTIVE;
                break;
            case 2:
                status = LoanStatus.FINISHED;
                break;
            default:
                status = null;
        }

        displayedLoans = library.getLoansByStatus(status);
        tableModel.setRowCount(0);
        for (Loan loan : displayedLoans) {
            tableModel.addRow(new Object[]{
                    loan.getId(),
                    loan.getBook().getTitle(),
                    loan.getMember().getName(),
                    formatDate(loan.getLoanDate()),
                    formatDate(loan.getReturnDate()),
                    loan.getStatus().getLabel()
            });
        }
    }

    private Loan getSelectedLoan() {
        int row = table.getSelectedRow();
        return row >= 0 ? displayedLoans.get(row) : null;
    }

    private String formatDate(LocalDate date) {
        return date == null ? "-" : date.format(DATE_FORMAT);
    }
}
