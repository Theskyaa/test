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


    @Query(nativeQuery = true,value = "select * from book where book_reading='null'")
    List<Book> listNotBorrowedBook();

    @Query(nativeQuery = true,value = "select * from book where book_reading=?1")
    List<Book> listBorrowedBookByEmail(String email);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update book set book_reading='null' where book_id=?1")
    int resetBookReading(Integer bookId);


    /*@Query(nativeQuery = true,value = "select * from book where book_name like ")
    List<Book> searchByBookName(String bookName);

    @Query(nativeQuery = true,value = "select * from book where book_author like '%'+?1+'%'")
    List<Book> searchByBookAuthor(String bookAuthor);*/

    List<Book> findByBookNameLike(String bookName);

    List<Book> findByBookAuthorLike(String bookAuthor);


















    @Transactional
    @Modifying
    @Query(value = "update book set book_reading =?2 where book.book_id=?1",nativeQuery = true)
    int updateBookReading(String bookId,String bookReading);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "insert into book (book_id, book_name, book_author, book_owner, book_reading, book_introduction, book_score, house_id) values (?1,?2,?3,?4,?5,?6,?7,?8)")
    int insertBook(int bookId,String bookName,String bookAuthor,String bookOwner,String bookReading,String bookIntroduction,int bookScore,int houseId);


    @Query(nativeQuery = true,value = "select book_name,publishinghouse.house_name,publishinghouse.house_telephone,book.book_introduction,book.book_score " +
            "from book,publishinghouse " +
            "where book.house_id=publishinghouse.house_id and book_reading='null'")
    List<Map<String,Object>> selectAllUsefulMessage();

    @Query(nativeQuery = true,value = "select * from book where book.book_name=?1")
    List<Book> selectByBookName(String bookName);





    @Query(value = "select * from book where book_name=?1",nativeQuery = true)
    List<Book> listByBookName(String bookName);

    @Query(value = "select * from book where book_id=?1",nativeQuery = true)
    List<Book> listByBookId(String bookId);

    @Query(nativeQuery = true,value =
            "select book.book_name,borrowrecord.borrow_time,return_time" +
            " from borrowrecord,returnrecord,book " +
            "where borrowrecord.borrow_id=returnrecord.borrow_id and borrowrecord.book_id=book.book_id and borrowrecord.email=?1")
    List<Map<String,Object>> selectBorrowAndReturnRecords(String email);

}
