/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.mycompany.librarymanagement.ui;

import com.mycompany.librarymanagement.logic.Library;
import com.mycompany.librarymanagement.model.Member;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
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
public class MemberManagementPanel extends javax.swing.JPanel {
    
    private Library library;

     
    private JTextField txtMemberId;
    private JTextField txtName;
    private JTextField txtContact;

    private JTable memberTable;
    private DefaultTableModel tableModel;

    
    public MemberManagementPanel(Library library) {
         this.library = library;
         
        initComponents();
        
        removeAll();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        initCustomComponents(); 
        loadAllMembers();  
    }
    
        private void initCustomComponents() {
       
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Member Details"));

        formPanel.add(new JLabel("Member ID:"));
        txtMemberId = new JTextField();
        formPanel.add(txtMemberId);

        formPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Contact Number:"));
        txtContact = new JTextField();
        formPanel.add(txtContact);

        add(formPanel, BorderLayout.NORTH);

        
        String[] columns = {"Member ID", "Name", "Contact Number"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        memberTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(memberTable);
        add(scrollPane, BorderLayout.CENTER);

        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton btnAdd = new JButton("Add Member");
        JButton btnSearch = new JButton("Search Member");
        JButton btnRefresh = new JButton("View All");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnRefresh);

        add(buttonPanel, BorderLayout.SOUTH);

        

        btnAdd.addActionListener(e -> {
            try {
                String id = txtMemberId.getText().trim();
                String name = txtName.getText().trim();
                String contact = txtContact.getText().trim();

                if (name.isEmpty() || contact.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Name and Contact Number cannot be empty.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Member newMember = new Member(id, name, contact);
                library.addMember(newMember); // Calls library logic[cite: 1]
                
                JOptionPane.showMessageDialog(this, "Member added successfully!");
                clearForm();
                loadAllMembers();
                
            } catch (Exception ex) {
                
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnSearch.addActionListener(e -> {
            try {
                String searchId = JOptionPane.showInputDialog(this, "Enter Member ID to search:");
                if (searchId != null && !searchId.trim().isEmpty()) {
                    
                    Member member = library.searchMember(searchId.trim());
                    tableModel.setRowCount(0); 
                    addMemberToTable(member);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Search Result", JOptionPane.INFORMATION_MESSAGE);
                loadAllMembers(); 
            }
        });

        btnRefresh.addActionListener(e -> loadAllMembers());
    }

    private void loadAllMembers() {
        tableModel.setRowCount(0); 
        List<Member> members = library.getAllMembers();
        for (Member member : members) {
            addMemberToTable(member);
        }
    }

    


    private void addMemberToTable(Member member) {
        tableModel.addRow(new Object[]{
            member.getId(), 
            member.getName(), 
            member.getContactNumber()
        });
    }

    private void clearForm() {
        txtMemberId.setText("");
        txtName.setText("");
        txtContact.setText(""); 
        txtMemberId.requestFocus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

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

    private void initCustomComponents() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void loadAllMembers() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
