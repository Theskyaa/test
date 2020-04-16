package com.database.test.repository;

import com.database.test.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface BookRepository extends JpaRepository<Book,String> {


    @Query(nativeQuery = true,value = "select * from book")
    List<Book> listAll();


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update book set book_reading=?2 where book_id=?1")
    int updateBookReadingByBookId(Integer bookId,String bookReadingEmail);


    @Query(nativeQuery = true,value = "select book.book_id,book_name,book_author,book_owner,book_reading,book_publishinghouse,book_score,book_introduction from book,bookbelong where bookbelong.group_id=?1 and bookbelong.book_id=book.book_id and book_reading='null'")
    List<Book> listNotBorrowedBookByGroupId(Integer groupId);

    @Query(nativeQuery = true,value = "select book.book_id,book_name,book_author,book_owner,book_reading,book_publishinghouse,book_score,book_introduction from book,bookbelong where group_id=?1 and book_reading=?2 and bookbelong.book_id=book.book_id")
    List<Book> listBorrowedBookByEmailAndGroupId(Integer groupId,String email);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update book set book_reading='null' where book_id=?1")
    int resetBookReading(Integer bookId);


    @Query(nativeQuery = true,value = "select * from book order by book_id desc limit 1")
    List<Book> selectMaxBookId();

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "insert into book (book_id, book_name, book_author, book_owner, book_reading, book_publishinghouse, book_score, book_introduction) values (?1,?2,?3,?4,?5,?6,?7,?8)")
    void insertBook(Integer bookId,String bookName,String bookAuthor,String bookOwner,String bookReading,String publishingHouse,Integer score,String bookIntroduction);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "insert into bookbelong (book_id, group_id) values (?1,?2)")
    void insertBookBelong(Integer bookId,Integer groupId);


    @Query(nativeQuery = true,value = "select book.book_id,book_name,book_author,book_owner,book_reading,book_publishinghouse,book_score,book_introduction from book,bookbelong where book_owner=?1 and group_id=?2 and book.book_id=bookbelong.book_id")
    List<Book> selectBookByBookOwnerAndGroupId(String bookOwner,Integer groupId);


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "delete from book where book_id=?1 and book_reading='null'")
    int deleteBookByBookID(Integer bookID);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "delete from bookbelong where book_id=?1 and group_id=?2")
    void deleteBookBelongByBookIdAndGroupId(Integer bookID,Integer groupId);



}
