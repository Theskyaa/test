package com.database.test.repository;

import com.database.test.entity.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BookReviewRepository extends JpaRepository<BookReview,Integer> {
    @Query(nativeQuery = true, value = "select Users.username,BookReview.review_time,BookReview.review_text " +
            "from BookReview,Library.dbo.LibraryItem,Users " +
            "where Users.email=BookReview.email and LibraryItem.book_id=BookReview.book_id and BookReview.book_id=?1")
    List<Object> selectBookReviewByBookID(String bookID);



    @Query(nativeQuery = true,value = "select book.book_name,review_time,review_text from book,bookreview where bookreview.email=?1 and book.book_id=bookreview.book_id")
    List<Object> selectBookReviewByEmail(String email);

    @Query(nativeQuery = true,value = "select username,review_time,review_text from user,book,bookreview where user.email=bookreview.email and book.book_id=bookreview.book_id and book.book_name=?1")
    List<Object> selectBookReviewBuBookName(String bookName);


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "insert into bookreview (review_id, email, book_id, review_time, review_text) values (?1,?2,?3,?4,?5)")
    int insertBookReview(int reviewId,String email,String bookId,String reviewTime,String reviewText);




}
