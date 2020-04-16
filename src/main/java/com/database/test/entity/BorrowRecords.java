package com.database.test.entity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "borrowrecord")
public class BorrowRecords {

    @Id
    @Column(name = "record_id")
    private Integer recordId;

    @Column(name = "email")
    private String email;

    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "borrow_time")
    private String borrowTime;

    @Column(name = "return_time")
    private String returnTime;

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }
}
