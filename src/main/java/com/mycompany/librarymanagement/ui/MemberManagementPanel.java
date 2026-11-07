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

public class MemberManagementPanel extends javax.swing.JPanel {
    private final Library library;
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

        tableModel = new DefaultTableModel(
                new String[]{"Member ID", "Name", "Contact Number"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        memberTable = new JTable(tableModel);
        add(new JScrollPane(memberTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnAdd = new JButton("Add Member");
        JButton btnSearch = new JButton("Search Member");
        JButton btnRefresh = new JButton("View All");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnRefresh);
        add(buttonPanel, BorderLayout.SOUTH);
        btnAdd.addActionListener(e -> addMember());
        btnSearch.addActionListener(e -> searchMember());
        btnRefresh.addActionListener(e -> loadAllMembers());
    }

    private void addMember() {
        try {
            String name = txtName.getText().trim();
            String contact = txtContact.getText().trim();
            if (name.isEmpty() || contact.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name and Contact Number cannot be empty.",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            library.addMember(new Member(txtMemberId.getText().trim(), name, contact));
            JOptionPane.showMessageDialog(this, "Member added successfully!");
            clearForm();
            loadAllMembers();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchMember() {
        String id = JOptionPane.showInputDialog(this, "Enter Member ID to search:");
        if (id == null || id.trim().isEmpty()) {
            return;
        }
        try {
            tableModel.setRowCount(0);
            addMemberToTable(library.searchMember(id.trim()));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Search Result",
                    JOptionPane.INFORMATION_MESSAGE);
            loadAllMembers();
        }
    }

    private void loadAllMembers() {
        tableModel.setRowCount(0);
        List<Member> members = library.getAllMembers();
        for (Member member : members) {
            addMemberToTable(member);
        }
    }

    private void addMemberToTable(Member member) {
        tableModel.addRow(new Object[]{member.getId(), member.getName(), member.getContactNumber()});
    }

    private void clearForm() {
        txtMemberId.setText("");
        txtName.setText("");
        txtContact.setText("");
        txtMemberId.requestFocus();
    }

    private void initComponents() {
        setPreferredSize(new java.awt.Dimension(400, 300));
    }
}
