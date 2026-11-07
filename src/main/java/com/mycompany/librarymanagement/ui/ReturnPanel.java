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

public class ReturnPanel extends javax.swing.JPanel {
    private final Library library;
    private JTextField txtBookId;

    public ReturnPanel(Library library) {
        this.library = library;
        initComponents();
        removeAll();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        initCustomComponents();
    }

    private void initCustomComponents() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Return a Book"));
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
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnReturn = new JButton("Return Book");
        formPanel.add(btnReturn, gbc);
        add(formPanel, BorderLayout.CENTER);

        btnReturn.addActionListener(e -> {
            String bookId = txtBookId.getText().trim();
            try {
                library.returnBook(bookId);
                JOptionPane.showMessageDialog(this, "Book successfully returned!\nBook ID: " + bookId,
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                txtBookId.setText("");
                txtBookId.requestFocus();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),
                        "Return Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void initComponents() {
        setPreferredSize(new java.awt.Dimension(400, 300));
    }
}
