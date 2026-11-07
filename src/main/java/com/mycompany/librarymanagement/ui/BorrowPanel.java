package com.mycompany.librarymanagement.ui;

import com.mycompany.librarymanagement.logic.Library;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BorrowPanel extends javax.swing.JPanel {
    private final Library library;
    private JTextField txtBookId;
    private JTextField txtMemberId;

    public BorrowPanel(Library library) {
        this.library = library;
        initComponents();
        removeAll();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        initCustomComponents();
    }

    private void initCustomComponents() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Borrow a Book"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Book ID:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        txtBookId = new JTextField(15);
        formPanel.add(txtBookId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        formPanel.add(new JLabel("Member ID:"), gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        txtMemberId = new JTextField(15);
        formPanel.add(txtMemberId, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnBorrow = new JButton("Borrow Book");
        formPanel.add(btnBorrow, gbc);
        add(formPanel, BorderLayout.CENTER);

        btnBorrow.addActionListener(e -> {
            String bookId = txtBookId.getText().trim();
            String memberId = txtMemberId.getText().trim();
            try {
                library.borrowBook(bookId, memberId);
                JOptionPane.showMessageDialog(this,
                        "Book successfully borrowed!\nBook ID: " + bookId + "\nMember ID: " + memberId,
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                txtBookId.setText("");
                txtMemberId.setText("");
                txtBookId.requestFocus();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Borrowing Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void initComponents() {
        setPreferredSize(new java.awt.Dimension(400, 300));
    }
}
