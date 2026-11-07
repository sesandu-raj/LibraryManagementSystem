package com.mycompany.librarymanagement.logic;

import com.mycompany.librarymanagement.db.BookDAO;
import com.mycompany.librarymanagement.db.BorrowRecordDAO;
import com.mycompany.librarymanagement.db.MemberDAO;
import com.mycompany.librarymanagement.model.Book;
import com.mycompany.librarymanagement.model.BorrowRecord;
import com.mycompany.librarymanagement.model.Member;
import java.util.Date;
import java.util.List;

public class Library {
    private final BookDAO bookDAO = new BookDAO();
    private final MemberDAO memberDAO = new MemberDAO();
    private final BorrowRecordDAO borrowRecordDAO = new BorrowRecordDAO();

    public void addBook(Book book) throws Exception {
        if (book.getItemId() == null || book.getItemId().trim().isEmpty()) {
            throw new Exception("Book ID cannot be empty.");
        }
        if (bookDAO.getBookById(book.getItemId()) != null) {
            throw new Exception("A book with this ID already exists.");
        }
        if (!bookDAO.addBook(book)) {
            throw new Exception("Failed to add the book.");
        }
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public Book searchBook(String bookId) throws Exception {
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new Exception("Please enter a Book ID to search.");
        }
        Book book = bookDAO.getBookById(bookId.trim());
        if (book == null) {
            throw new Exception("Book not found.");
        }
        return book;
    }

    public void removeBook(String bookId) throws Exception {
        Book book = searchBook(bookId);
        if (!book.isAvailable()) {
            throw new Exception("Cannot remove a book that is currently borrowed.");
        }
        if (!bookDAO.deleteBook(bookId)) {
            throw new Exception("Failed to remove the book.");
        }
    }

    public void addMember(Member member) throws Exception {
        if (member.getId() == null || member.getId().trim().isEmpty()) {
            throw new Exception("Member ID cannot be empty.");
        }
        if (memberDAO.getMemberById(member.getId()) != null) {
            throw new Exception("A member with this ID already exists.");
        }
        if (!memberDAO.addMember(member)) {
            throw new Exception("Failed to add the member.");
        }
    }

    public List<Member> getAllMembers() {
        return memberDAO.getAllMembers();
    }

    public Member searchMember(String memberId) throws Exception {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new Exception("Please enter a Member ID to search.");
        }
        Member member = memberDAO.getMemberById(memberId.trim());
        if (member == null) {
            throw new Exception("Member does not exist.");
        }
        return member;
    }

    public void borrowBook(String bookId, String memberId) throws Exception {
        if (bookId == null || bookId.trim().isEmpty()
                || memberId == null || memberId.trim().isEmpty()) {
            throw new Exception("Book ID and Member ID are required.");
        }

        Book book = bookDAO.getBookById(bookId.trim());
        if (book == null) {
            throw new Exception("Book does not exist.");
        }
        if (memberDAO.getMemberById(memberId.trim()) == null) {
            throw new Exception("Member does not exist.");
        }
        if (!book.isAvailable()) {
            throw new Exception("This book is already borrowed and not available.");
        }

        BorrowRecord record = new BorrowRecord(
                "REC-" + System.currentTimeMillis(), book.getItemId(), memberId.trim(),
                new Date(), null, "Borrowed");
        if (!borrowRecordDAO.addBorrowRecord(record)) {
            throw new Exception("Failed to create borrow record.");
        }
        if (!bookDAO.updateBookStatus(book.getItemId(), false)) {
            throw new Exception("Failed to update book availability status.");
        }
    }

    public void returnBook(String bookId) throws Exception {
        if (bookId == null || bookId.trim().isEmpty()) {
            throw new Exception("Book ID is required.");
        }
        Book book = bookDAO.getBookById(bookId.trim());
        if (book == null) {
            throw new Exception("Book does not exist.");
        }
        if (book.isAvailable()) {
            throw new Exception("This book is not currently borrowed.");
        }

        BorrowRecord activeRecord = borrowRecordDAO.getActiveRecordByBookId(book.getItemId());
        if (activeRecord == null) {
            throw new Exception("No active borrow record found for this book.");
        }
        if (!borrowRecordDAO.markBookAsReturned(activeRecord.getRecordId(), new Date())) {
            throw new Exception("Failed to update borrow record.");
        }
        if (!bookDAO.updateBookStatus(book.getItemId(), true)) {
            throw new Exception("Failed to update book availability status.");
        }
    }

    public int getTotalBooksCount() {
        return bookDAO.getAllBooks().size();
    }

    public int getAvailableBooksCount() {
        return (int) bookDAO.getAllBooks().stream().filter(Book::isAvailable).count();
    }

    public int getBorrowedBooksCount() {
        return (int) bookDAO.getAllBooks().stream().filter(book -> !book.isAvailable()).count();
    }

    public int getTotalMembersCount() {
        return memberDAO.getAllMembers().size();
    }
}
