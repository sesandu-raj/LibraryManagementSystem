package com.mycompany.librarymanagement.ui;

import com.mycompany.librarymanagement.logic.Library;
import com.mycompany.librarymanagement.model.Book;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class BookManagementPanel extends javax.swing.JPanel {
    private final Library library;
    private JTextField txtBookId;
    private JTextField txtTitle;
    private JTextField txtAuthor;
    private JComboBox<String> cmbCategory;
    private JTable bookTable;
    private DefaultTableModel tableModel;

    public BookManagementPanel(Library library) {
        this.library = library;
        initComponents();
        removeAll();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        initCustomComponents();
        loadAllBooks();
    }

    private void initCustomComponents() {
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Book Details"));
        formPanel.add(new JLabel("Book ID:"));
        txtBookId = new JTextField();
        formPanel.add(txtBookId);
        formPanel.add(new JLabel("Title:"));
        txtTitle = new JTextField();
        formPanel.add(txtTitle);
        formPanel.add(new JLabel("Author:"));
        txtAuthor = new JTextField();
        formPanel.add(txtAuthor);
        formPanel.add(new JLabel("Category:"));
        cmbCategory = new JComboBox<>(new String[]{
            "Programming", "Networking", "Database", "Cyber Security", "Other"
        });
        formPanel.add(cmbCategory);
        add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new String[]{"Book ID", "Title", "Author", "Category", "Status"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        bookTable = new JTable(tableModel);
        add(new JScrollPane(bookTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnAdd = new JButton("Add Book");
        JButton btnSearch = new JButton("Search Book");
        JButton btnRemove = new JButton("Remove Book");
        JButton btnRefresh = new JButton("View All");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnRemove);
        buttonPanel.add(btnRefresh);
        add(buttonPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addBook());
        btnSearch.addActionListener(e -> searchBook());
        btnRemove.addActionListener(e -> removeBook());
        btnRefresh.addActionListener(e -> loadAllBooks());
    }

    private void addBook() {
        try {
            String title = txtTitle.getText().trim();
            String author = txtAuthor.getText().trim();
            if (title.isEmpty() || author.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Title and Author cannot be empty.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            library.addBook(new Book(txtBookId.getText().trim(), title, true, author,
                    cmbCategory.getSelectedItem().toString()));
            JOptionPane.showMessageDialog(this, "Book added successfully!");
            clearForm();
            loadAllBooks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchBook() {
        String id = JOptionPane.showInputDialog(this, "Enter Book ID to search:");
        if (id == null || id.trim().isEmpty()) {
            return;
        }
        try {
            tableModel.setRowCount(0);
            addBookToTable(library.searchBook(id.trim()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Search Result",
                    JOptionPane.INFORMATION_MESSAGE);
            loadAllBooks();
        }
    }

    private void removeBook() {
        int selectedRow = bookTable.getSelectedRow();
        String id = selectedRow >= 0
                ? tableModel.getValueAt(bookTable.convertRowIndexToModel(selectedRow), 0).toString()
                : JOptionPane.showInputDialog(this, "Enter Book ID to remove:");
        if (id == null || id.trim().isEmpty()) {
            return;
        }
        if (JOptionPane.showConfirmDialog(this, "Are you sure you want to remove Book ID: " + id + "?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            library.removeBook(id.trim());
            JOptionPane.showMessageDialog(this, "Book removed successfully!");
            loadAllBooks();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadAllBooks() {
        tableModel.setRowCount(0);
        List<Book> books = library.getAllBooks();
        for (Book book : books) {
            addBookToTable(book);
        }
    }

    private void addBookToTable(Book book) {
        tableModel.addRow(new Object[]{
            book.getItemId(), book.getTitle(), book.getAuthor(), book.getCategory(),
            book.isAvailable() ? "Available" : "Borrowed"
        });
    }

    private void clearForm() {
        txtBookId.setText("");
        txtTitle.setText("");
        txtAuthor.setText("");
        cmbCategory.setSelectedIndex(0);
        txtBookId.requestFocus();
    }

    private void initComponents() {
        setPreferredSize(new java.awt.Dimension(400, 300));
    }
}
