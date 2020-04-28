package com.database.test.repository;

import com.database.test.entity.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface BookReviewRepository extends JpaRepository<BookReview,Integer> {
    @Query(nativeQuery = true,value = "select review_id, review_time, review_text, review_star, username from bookreview,user where book_id=?1 and user.email=bookreview.email")
    List<Map<String,Object>> selectBookReviewByBookId(Integer bookId);


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "delete from bookreview where book_id in (select book_id from bookbelong where  bookbelong.group_id=?1)")
    void deleteByBookIdFromBookBelong(Integer groupId);

    @Query(nativeQuery = true,value = "select * from bookreview order by review_id desc limit 1")
    List<BookReview> selectMaxReviewId();


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "insert into bookreview (review_id, review_time, review_text, review_star, email, book_id) values (?1,?2,?3,?4,?5,?6)")
    void insertReviewRecord(Integer reviewId,String time,String text,Integer reviewStar,String email,Integer bookID);


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "delete from bookreview where review_id=?1")
    void deleteByReviewId(Integer reviewId);

}
