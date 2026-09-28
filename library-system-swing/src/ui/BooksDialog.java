package ui;

import entities.Book;
import entities.Ebook;
import entities.Library;
import entities.LibraryException;
import entities.PhysicalBook;
import util.IsbnUtils;
import util.TextUtils;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import java.time.Year;

public class BooksDialog extends JDialog {

    private final Library library;

    private final JTextField txtId = new JTextField();
    private final JTextField txtTitle = new JTextField(20);
    private final JTextField txtAuthor = new JTextField(20);
    private final JTextField txtYear = new JTextField(6);
    private final JTextField txtIsbn = new JTextField(17);
    private final JComboBox<String> cbType = new JComboBox<>(new String[]{"Físico", "E-book"});

    private final DefaultTableModel tableModel = new DefaultTableModel(
            new Object[]{"ID", "Título", "Autor", "Ano", "ISBN", "Tipo", "Situação"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable table = new JTable(tableModel);

    public BooksDialog(Frame owner, Library library) {
        super(owner, "Gerenciamento de Livros", true);
        this.library = library;

        configureInputs();
        buildLayout();
        refreshTable();

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(900, 520);
        setLocationRelativeTo(owner);
    }

    private void configureInputs() {
        txtId.setEditable(false);
        InputFilters.limit(txtTitle, Library.MAX_TEXT_LENGTH);
        InputFilters.limit(txtAuthor, Library.MAX_TEXT_LENGTH);
        InputFilters.digitsOnly(txtYear, 4);
        InputFilters.digitMask(txtIsbn, 13, IsbnUtils::format);
        txtIsbn.setToolTipText("ISBN brasileiro: 978-65-XXXXXXX-X ou 978-85-XXXXXXX-X");
        txtYear.setToolTipText("Ano com 4 dígitos, ex.: 2024");
    }

    private void buildLayout() {
        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        form.setBorder(BorderFactory.createTitledBorder("Dados do livro"));
        form.add(new JLabel("ID:"));
        form.add(txtId);
        form.add(new JLabel("Título (até 150):"));
        form.add(txtTitle);
        form.add(new JLabel("Autor (até 150, só letras):"));
        form.add(txtAuthor);
        form.add(new JLabel("Ano (AAAA):"));
        form.add(txtYear);
        form.add(new JLabel("ISBN (978-65 ou 978-85):"));
        form.add(txtIsbn);
        form.add(new JLabel("Tipo:"));
        form.add(cbType);

        JButton btnAdd = new JButton("Cadastrar livro");
        JButton btnEdit = new JButton("Editar selecionado");
        JButton btnRemove = new JButton("Remover livro");
        JButton btnClear = new JButton("Limpar campos");

        btnAdd.addActionListener(e -> addBook());
        btnEdit.addActionListener(e -> editBook());
        btnRemove.addActionListener(e -> removeBook());
        btnClear.addActionListener(e -> clearForm());

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(btnAdd);
        buttons.add(btnEdit);
        buttons.add(btnRemove);
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

    private void addBook() {
        try {
            String title = txtTitle.getText().trim();
            String author = txtAuthor.getText().trim();
            Year year = parseYear();
            String isbn = TextUtils.onlyDigits(txtIsbn.getText());

            Book book = cbType.getSelectedIndex() == 0
                    ? new PhysicalBook(title, author, year, isbn)
                    : new Ebook(title, author, year, isbn);

            library.addBook(book);
            refreshTable();
            clearForm();
            Dialogs.info(this, "Livro cadastrado com sucesso! ID: " + book.getId());
        } catch (LibraryException ex) {
            Dialogs.error(this, ex.getMessage());
        }
    }

    private void editBook() {
        try {
            library.updateBook(getSelectedBook(),
                    txtTitle.getText().trim(),
                    txtAuthor.getText().trim(),
                    parseYear(),
                    TextUtils.onlyDigits(txtIsbn.getText()));
            refreshTable();
            clearForm();
            Dialogs.info(this, "Livro atualizado com sucesso!");
        } catch (LibraryException ex) {
            Dialogs.error(this, ex.getMessage());
        }
    }

    private void removeBook() {
        Book book = getSelectedBook();
        if (book != null && !Dialogs.confirm(this, "Remover o livro \"" + book.getTitle() + "\"?")) {
            return;
        }
        try {
            library.removeBook(book);
            refreshTable();
            clearForm();
            Dialogs.info(this, "Livro removido.");
        } catch (LibraryException ex) {
            Dialogs.error(this, ex.getMessage());
        }
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Book book : library.getBooks()) {
            tableModel.addRow(new Object[]{
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getYear().toString(),
                    book.getFormattedIsbn(),
                    book.getType(),
                    book.isAvailable() ? "Disponível" : "Emprestado"
            });
        }
    }

    private Book getSelectedBook() {
        int row = table.getSelectedRow();
        if (row < 0) {
            return null;
        }
        int id = (Integer) tableModel.getValueAt(row, 0);
        return library.findBookById(id);
    }

    private void fillFormWithSelected() {
        Book book = getSelectedBook();
        if (book == null) {
            return;
        }
        txtId.setText(String.valueOf(book.getId()));
        txtTitle.setText(book.getTitle());
        txtAuthor.setText(book.getAuthor());
        txtYear.setText(book.getYear().toString());
        txtIsbn.setText(book.getIsbn());
        cbType.setSelectedItem(book.getType());
        cbType.setEnabled(false);
    }

    private void clearForm() {
        table.clearSelection();
        txtId.setText("");
        txtTitle.setText("");
        txtAuthor.setText("");
        txtYear.setText("");
        txtIsbn.setText("");
        cbType.setSelectedIndex(0);
        cbType.setEnabled(true);
        txtTitle.requestFocus();
    }

    private Year parseYear() throws LibraryException {
        String text = txtYear.getText().trim();
        if (!text.matches("\\d{4}")) {
            throw new LibraryException("Informe o ano com 4 dígitos (ex.: 2024).");
        }
        return Year.of(Integer.parseInt(text));
    }
}
