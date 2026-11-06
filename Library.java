/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.librarymanagementsystem;

/**
 *
 * @author My PC
 */

  

// --- IMPORTS ---
// Bringing in all the data filing cabinets (Level 2)
import com.mycompany.librarymanagementsystem.db.BookDAO;
import com.mycompany.librarymanagementsystem.db.MemberDAO;
import com.mycompany.librarymanagementsystem.db.BorrowRecordDAO;

// Bringing in all the object blueprints (Level 1)
import com.mycompany.librarymanagementsystem.model.Book;
import com.mycompany.librarymanagementsystem.model.Member;
import com.mycompany.librarymanagementsystem.model.BorrowRecord;

import java.util.Date;
import java.util.List;

public class Library {
    
    // 1. Declare the DAOs
    private BookDAO bookDAO;
    private MemberDAO memberDAO;
    private BorrowRecordDAO borrowRecordDAO;

    // 2. Constructor: Initialize the system
    public Library() {
        this.bookDAO = new BookDAO();
        this.memberDAO = new MemberDAO();
        this.borrowRecordDAO = new BorrowRecordDAO();
    }
  


// ==========================================
// BOOK MANAGEMENT DELEGATION
// ==========================================

public boolean addBook(Book book) {
    // Business Rule: Prevent duplicate Book IDs
    if (bookDAO.getBookById(book.getItemId()) != null) {
        return false; // Book ID already exists, reject it
    }
    return bookDAO.addBook(book);
}

public List<Book> getAllBooks() {
    return bookDAO.getAllBooks();
}

public boolean deleteBook(String bookId) {
    return bookDAO.deleteBook(bookId);
}

// ==========================================
// MEMBER MANAGEMENT DELEGATION
// ==========================================

public boolean addMember(Member member) {
    // Business Rule: Prevent duplicate Member IDs
    if (memberDAO.getMemberById(member.getId()) != null) {
        return false; // Member ID already exists, reject it
    }
    return memberDAO.addMember(member);
}

public List<Member> getAllMembers() {
    return memberDAO.getAllMembers();
}
public boolean deleteMember(String memberId) {
    return memberDAO.deleteMember(memberId);
}
// ============================================
// TRANSACTION LOGIC (The Core Business Rules)
// ============================================

public String borrowBook(String bookId, String memberId) {
    Book book = bookDAO.getBookById(bookId);
    Member member = memberDAO.getMemberById(memberId);

    // Rule 1: Check if book exists
    if (book == null) return "Error: Book not found.";

    // Rule 2: Check if member exists
    if (member == null) return "Error: Member not found.";

    // Rule 3: Check if book is already taken
    if (!book.isAvailable()) return "Error: Book is currently unavailable.";

    // Rule 4: Generate a unique receipt ID using the current millisecond time
    String recordId = "REC-" + System.currentTimeMillis();

    // Create the transaction record and save it
    BorrowRecord record = new BorrowRecord(recordId, bookId, memberId, new Date(), "Borrowed");
    borrowRecordDAO.addBorrowRecord(record);

    // Update the physical book's status to false (unavailable)
    bookDAO.updateBookStatus(bookId, false);

    return "Success: Book borrowed successfully!";
}
public String returnBook(String bookId) {
    Book book = bookDAO.getBookById(bookId);

// Rule 1: Check if book exists in the library catalog
if (book == null) return "Error: Book not found.";

// Rule 2: Find the active transaction for this book
BorrowRecord record = borrowRecordDAO.getActiveRecordByBookId(bookId);
if (record == null) return "Error: No active borrow record found for this book.";

// Close the transaction and mark the book as available again
borrowRecordDAO.markBookAsReturned(record.getRecordId(), new Date());
bookDAO.updateBookStatus(bookId, true);

return "Success: Book returned successfully!";
}
public int getTotalBooks() {
    return bookDAO.getAllBooks().size();
}
public int getTotalMembers() {
    return memberDAO.getAllMembers().size();
}
public int getBorrowedBooksCount() {
    int count = 0;
    for (Book book : bookDAO.getAllBooks()) {
        if (!book.isAvailable()) count++; // Count how many are currently marked false
    }
    return count;
}
}
