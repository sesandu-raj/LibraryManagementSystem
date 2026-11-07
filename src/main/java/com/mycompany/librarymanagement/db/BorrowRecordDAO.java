/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.librarymanagement.db;

import com.mycompany.librarymanagement.model.BorrowRecord;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BorrowRecordDAO {
    // Using an ArrayList to store system data as requested[cite: 2]
    private List<BorrowRecord> records;

    public BorrowRecordDAO() {
        this.records = new ArrayList<>();
    }

    public boolean addBorrowRecord(BorrowRecord record) {
        return records.add(record);
    }

    public BorrowRecord getActiveRecordByBookId(String bookId) {
        for (BorrowRecord record : records) {
            if (record.getBookId().equals(bookId) && "Borrowed".equals(record.getStatus())) {
                return record;
            }
        }
        return null;
    }

    public boolean markBookAsReturned(String recordId, Date returnDate) {
        for (BorrowRecord record : records) {
            if (record.getRecordId().equals(recordId)) {
                record.setReturnDate(returnDate);
                record.setStatus("Returned");
                return true;
            }
        }
        return false;
    }

    public List<BorrowRecord> getAllBorrowRecords() {
        return records;
    }
}
