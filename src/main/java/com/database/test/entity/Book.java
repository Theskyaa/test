package com.database.test.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @Column(name = "book_id")
    public String bookId;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "book_author")
    private String bookAuthor;

    @Column(name = "book_owner")
    private String bookOwner;

    @Column(name = "book_reading")
    private String bookCurrentReading;

    @Column(name = "book_introduction")
    private String bookIntroduction;

    @Column(name = "book_score")
    private Integer bookScore;

    @Column(name = "house_id")
    private Integer houseId;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookOwner() {
        return bookOwner;
    }

    public void setBookOwner(String bookOwner) {
        this.bookOwner = bookOwner;
    }

    public String getBookCurrentReading() {
        return bookCurrentReading;
    }

    public void setBookCurrentReading(String bookCurrentReading) {
        this.bookCurrentReading = bookCurrentReading;
    }

    public String getBookIntroduction() {
        return bookIntroduction;
    }

    public void setBookIntroduction(String bookIntroduction) {
        this.bookIntroduction = bookIntroduction;
    }

    public Integer getHouseId() {
        return houseId;
    }

    public void setHouseId(Integer houseId) {
        this.houseId = houseId;
    }

    public Integer getBookScore() {
        return bookScore;
    }

    public void setBookScore(Integer bookScore) {
        this.bookScore = bookScore;
    }

}
