/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.librarymanagement;

import com.mycompany.librarymanagement.logic.Library;
import com.mycompany.librarymanagement.ui.MainFrame;
import javax.swing.SwingUtilities;

public class LibraryManagement {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Library library = new Library();
            new MainFrame(library).setVisible(true);
        });
    }
}
