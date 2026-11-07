

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.librarymanagementsystem;

import com.mycompany.librarymanagementsystem.logic.Library;

import javax.swing.*;
import java.awt.*;

public class BorrowPanel extends javax.swing.JPanel {

    private Library library;

    // Form Input Fields
    private JTextField txtBookId;
    private JTextField txtMemberId;

    public BorrowPanel(Library library) {
        this.library = library;

        initComponents(); // 1. NetBeans locked code

        // 2. Clear default layout and apply our own layout with heavy padding
        removeAll();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        // Pushes the form to the center
        
     initCustomComponents(); // 3. Build the UI[span_1](start_span)[span_1](end_span)
}

private void initCustomComponents() {
    // --- Header ---[span_4](start_span)[span_4](end_span)
    JLabel lblTitle = new JLabel("Issue Book to Member", SwingConstants.CENTER);
    lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
    lblTitle.setForeground(new Color(44, 62, 80));
    add(lblTitle, BorderLayout.NORTH);

    // --- Form Panel (Centered) ---[span_9](start_span)[span_9](end_span)
    // Using a GridLayout for uniform spacing of the form fields[span_10](start_span)[span_10](end_span)
    JPanel formPanel = new JPanel(new GridLayout(4, 1, 10, 10));
    formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

    formPanel.add(new JLabel("Enter Book ID:"));
    txtBookId = new JTextField();
    txtBookId.setFont(new Font("Arial", Font.PLAIN, 16));
    formPanel.add(txtBookId);

    formPanel.add(new JLabel("Enter Member ID:"));
    txtMemberId = new JTextField();
    txtMemberId.setFont(new Font("Arial", Font.PLAIN, 16));
    formPanel.add(txtMemberId);

    // Add the form to the center of the screen[span_21](start_span)[span_21](end_span)
    add(formPanel, BorderLayout.CENTER);

    // --- Button Panel (Bottom) ---[span_23](start_span)[span_23](end_span)
    JPanel buttonPanel = new JPanel();
    JButton btnBorrow = new JButton("Borrow Book");
    btnBorrow.setFont(new Font("Arial", Font.BOLD, 16));
    btnBorrow.setBackground(new Color(46, 204, 113)); // A nice green color[span_27](start_span)[span_27](end_span)
    btnBorrow.setForeground(Color.WHITE);
    btnBorrow.setFocusPainted(false);
    btnBorrow.setPreferredSize(new Dimension(200, 40));
    
    buttonPanel.add(btnBorrow);
    add(buttonPanel, BorderLayout.SOUTH);

    // --- Action Listeners ---[span_2](start_span)[span_2](end_span)
    btnBorrow.addActionListener(e -> {
        String bookId = txtBookId.getText().trim();
        String memberId = txtMemberId.getText().trim();

        // 1. Basic UI Validation[span_6](start_span)[span_6](end_span)
        if (bookId.isEmpty() || memberId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both Book ID and Member ID.", "Missing Information",
JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Pass to Logic Layer and capture the result string[span_12](start_span)[span_12](end_span)
        String result = library.borrowBook(bookId, memberId);

        // 3. Display the result to the user[span_14](start_span)[span_14](end_span)
        if (result.startsWith("Success")) {
            JOptionPane.showMessageDialog(this, result,
"Transaction Successful", JOptionPane.INFORMATION_MESSAGE);
            clearFields(); // Reset the form for the next person in line
        }
        else {
            // If it starts with "Error", show a warning popup[span_21](start_span)[span_21](end_span)
            JOptionPane.showMessageDialog(this, result,
"Transaction Failed", JOptionPane.ERROR_MESSAGE);
        }
    });
}

private void clearFields() {
    txtBookId.setText("");
    txtMemberId.setText("");
    txtBookId.requestFocus();
   }
