/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.librarymanagement.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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

/**
 *
 * @author sesanduramath
 */
public class BookManagementPanel extends javax.swing.JPanel {

     private Library library;
    
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
        String[] categories = {"Programming", "Networking", "Database", "Cyber Security", "Other"};
        cmbCategory = new JComboBox<>(categories);
        formPanel.add(cmbCategory);

        add(formPanel, BorderLayout.NORTH);
        
        String[] columns = {"Book ID", "Title", "Author", "Category", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        bookTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(bookTable);
        add(scrollPane, BorderLayout.CENTER);
  
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
        
  

        btnAdd.addActionListener(e -> {
            try {
                String id = txtBookId.getText().trim();
                String title = txtTitle.getText().trim();
                String author = txtAuthor.getText().trim();
                String category = cmbCategory.getSelectedItem().toString();

                if (title.isEmpty() || author.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Title and Author cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Book newBook = new Book(id, title, true, author, category);
                library.addBook(newBook);
                
                JOptionPane.showMessageDialog(this, "Book added successfully!");
                clearForm();
                loadAllBooks();
                
            } catch (Exception ex) {

                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSearch.addActionListener(e -> {
            try {
                String searchId = JOptionPane.showInputDialog(this, "Enter Book ID to search:");
                if (searchId != null && !searchId.trim().isEmpty()) {
                    Book book = library.searchBook(searchId.trim());
                    tableModel.setRowCount(0);
                    addBookToTable(book);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Search Result", JOptionPane.INFORMATION_MESSAGE);
                loadAllBooks();
            }
        });

        btnRemove.addActionListener(e -> {
            try {
 
                int selectedRow = bookTable.getSelectedRow();
                String removeId;
                if (selectedRow >= 0) {
                    removeId = tableModel.getValueAt(selectedRow, 0).toString();
                } else {
                    removeId = JOptionPane.showInputDialog(this, "Enter Book ID to remove:");
                }

                if (removeId != null && !removeId.trim().isEmpty()) {
                    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to remove Book ID: " + removeId + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        library.removeBook(removeId.trim());
                        JOptionPane.showMessageDialog(this, "Book removed successfully!");
                        loadAllBooks();
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnRefresh.addActionListener(e -> loadAllBooks());
    }

    
    private void loadAllBooks() {
        tableModel.setRowCount(0); 
        List<Book> books = library.getAllBooks();
        for (Book book : books) {
            addBookToTable(book);
        }
    }

    private void addBookToTable(Book book) {
        String status = book.isAvailable() ? "Available" : "Borrowed";
        tableModel.addRow(new Object[]{
            book.getItemId(), 
            book.getTitle(), 
            book.getAuthor(), 
            book.getCategory(), 
            status
        });
    }

    private void clearForm() {
        txtBookId.setText("");
        txtTitle.setText("");
        txtAuthor.setText("");
        cmbCategory.setSelectedIndex(0);
    }                 
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}
