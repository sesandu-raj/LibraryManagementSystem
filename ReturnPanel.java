/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.mycompany.librarymanagementsystem;

import com.mycompany.librarymanagementsystem.logic.Library;

import javax.swing.*;
import java.awt.*;

public class ReturnPanel extends javax.swing.JPanel {

    private Library library;

    // Form Input Field
    private JTextField txtBookId;

    public ReturnPanel(Library library) {
        this.library = library;

        initComponents(); // 1. NetBeans locked code

        // 2. Clear default layout and apply our centered layout
        removeAll();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));

        initCustomComponents(); // 3. Build the UI
    }

    private void initCustomComponents() {
        // --- Header ---
        JLabel lblTitle = new JLabel("Return a Book", SwingConstants.CENTER);
    lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
    lblTitle.setForeground(new Color(44, 62, 80));
    add(lblTitle, BorderLayout.NORTH);

    // --- Form Panel (Centered) ---[span_4](start_span)[span_4](end_span)
    // A simple 2-row grid for the label and the text box[span_5](start_span)[span_5](end_span)
    JPanel formPanel = new JPanel(new GridLayout(2, 1, 10, 10));
    formPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 100, 100)); // Extra padding to keep it compact[span_7](start_span)[span_7](end_span)

    formPanel.add(new JLabel("Enter Book ID to Return:"));
    txtBookId = new JTextField();
    txtBookId.setFont(new Font("Arial", Font.PLAIN, 16));
    formPanel.add(txtBookId);

    add(formPanel, BorderLayout.CENTER);

    // --- Button Panel (Bottom) ---[span_13](start_span)[span_13](end_span)
    JPanel buttonPanel = new JPanel();
    JButton btnReturn = new JButton("Process Return");
    btnReturn.setFont(new Font("Arial", Font.BOLD, 16));
    btnReturn.setBackground(new Color(52, 152, 219)); // A nice blue color[span_17](start_span)[span_17](end_span)
    btnReturn.setForeground(Color.WHITE);
    btnReturn.setFocusPainted(false);
    btnReturn.setPreferredSize(new Dimension(200, 40));

    buttonPanel.add(btnReturn);
    add(buttonPanel, BorderLayout.SOUTH);

    // --- Action Listeners ---[span_23](start_span)[span_23](end_span)
    btnReturn.addActionListener(e -> {
        String bookId = txtBookId.getText().trim();

        // 1. Basic UI Validation[span_26](start_span)[span_26](end_span)
        if (bookId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a Book ID.", "Missing Information", 
                    JOptionPane.WARNING_MESSAGE);
            return;
    }
        // 2. Pass to Logic Layer and capture the result string
 String result = library.returnBook(bookId);
 // 3. Display the result to the user
 if (result.startsWith("Success")) {
 JOptionPane.showMessageDialog(this, result, "Return Successful",
         JOptionPane.INFORMATION_MESSAGE);
 txtBookId.setText("");
 txtBookId.requestFocus(); // Put the blinking cursor back in the box
 }
 else {
 // If the logic layer rejected it (e.g., book wasn't borrowed, or doesn't exist)
 JOptionPane.showMessageDialog(this, result, "Return Failed",
         JOptionPane.ERROR_MESSAGE);
 }
    });
            }
}