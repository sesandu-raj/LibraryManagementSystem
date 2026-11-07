package com.mycompany.librarymanagement.ui;

import com.mycompany.librarymanagement.logic.Library;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class MainFrame extends JFrame {
    private static final Logger logger = Logger.getLogger(MainFrame.class.getName());
    private final Library library;
    private CardLayout cardLayout;
    private JPanel mainContentPanel;
    private Dashboard dashboardPanel;

    public MainFrame(Library library) {
        this.library = library;
        initComponents();
        initCustomComponents();
    }

    private void initCustomComponents() {
        getContentPane().removeAll();
        getContentPane().setLayout(new BorderLayout());
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        JPanel sidebarPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        sidebarPanel.setPreferredSize(new Dimension(200, getHeight()));
        sidebarPanel.setBackground(new Color(44, 62, 80));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        JButton btnDashboard = createNavButton("Dashboard");
        JButton btnManageBooks = createNavButton("Manage Books");
        JButton btnManageMembers = createNavButton("Manage Members");
        JButton btnBorrowBook = createNavButton("Borrow Book");
        JButton btnReturnBook = createNavButton("Return Book");
        JButton btnExit = createNavButton("Exit");
        sidebarPanel.add(btnDashboard);
        sidebarPanel.add(btnManageBooks);
        sidebarPanel.add(btnManageMembers);
        sidebarPanel.add(btnBorrowBook);
        sidebarPanel.add(btnReturnBook);
        sidebarPanel.add(btnExit);
        add(sidebarPanel, BorderLayout.WEST);

        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        dashboardPanel = new Dashboard(library);
        mainContentPanel.add(dashboardPanel, "Dashboard");
        mainContentPanel.add(new BookManagementPanel(library), "ManageBooks");
        mainContentPanel.add(new MemberManagementPanel(library), "ManageMembers");
        mainContentPanel.add(new BorrowPanel(library), "BorrowBook");
        mainContentPanel.add(new ReturnPanel(library), "ReturnBook");
        add(mainContentPanel, BorderLayout.CENTER);

        btnDashboard.addActionListener(e -> {
            dashboardPanel.refreshDashboard();
            cardLayout.show(mainContentPanel, "Dashboard");
        });
        btnManageBooks.addActionListener(e -> cardLayout.show(mainContentPanel, "ManageBooks"));
        btnManageMembers.addActionListener(e -> cardLayout.show(mainContentPanel, "ManageMembers"));
        btnBorrowBook.addActionListener(e -> cardLayout.show(mainContentPanel, "BorrowBook"));
        btnReturnBook.addActionListener(e -> cardLayout.show(mainContentPanel, "ReturnBook"));
        btnExit.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Are you sure you want to exit?",
                    "Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(52, 73, 94));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent event) {
                button.setBackground(new Color(41, 128, 185));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent event) {
                button.setBackground(new Color(52, 73, 94));
            }
        });
        return button;
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(Level.SEVERE, "Unable to set Nimbus look and feel", ex);
        }
        java.awt.EventQueue.invokeLater(() -> new MainFrame(new Library()).setVisible(true));
    }
}
