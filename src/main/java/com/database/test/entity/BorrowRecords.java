package com.database.test.entity;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "borrowrecord")
public class BorrowRecords {

    @Id
    @Column(name = "borrow_id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer borrowId;

    @Column(name = "email")
    private String email;

    @Column(name = "book_id")
    private String bookId;

    @Column(name = "borrow_time")
    private String borrowTime;

    @Column(name = "return_id")
    private Integer returnId;

    public Integer getReturnId() {
        return returnId;
    }

    public void setReturnId(Integer returnId) {
        this.returnId = returnId;
    }

    public Integer getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(Integer borrowId) {
        this.borrowId = borrowId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }
}
