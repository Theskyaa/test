package com.database.test.repository;

import com.database.test.entity.BorrowRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Column;
import javax.print.attribute.standard.MediaSize;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface BorrowRecordsRepository extends JpaRepository<BorrowRecords,Integer> {

    List<BorrowRecords> findByEmail(String email);

    /*@Modifying
    @Query(nativeQuery = true,value = "update BorrowingRecords set ")*/

    @Query(nativeQuery = true,value = "select BorrowRecord.email,book.book_name,book.book_author,borrowrecord.book_borrow_time " +
            "from borrowrecord,book " +
            "where borrowrecord.email=?1 and borrowrecord.book_id=book.book_id")
    List<Object> selectAllBorrowRecords(String email);

    @Query(nativeQuery = true,value = "select * from borrowrecord")
    List<BorrowRecords> listAllBorrowRecord();

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "insert into borrowrecord(borrow_id, email, book_id, borrow_time,return_id) values (?1,?2,?3,?4,?5)")
    int insertBorrowRecords(Integer borrowId,String email,Integer bookId,String borrowTime,Integer returnId);


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update borrowrecord set return_id=?2 where book_id=?3 and return_id=0 and email=?1")
    int updateBorrowRecord(String email,Integer returnId,String bookId);

    @Query(nativeQuery = true,value = "select * from borrowrecord where email=?1 and return_id=?2")
    List<Map<String,Object>> selectByEmailAndReturnId(String email,Integer returnId);


    @Query(nativeQuery = true,value = "select book.book_name,borrowrecord.borrow_time,return_time from book,borrowrecord,returnrecord where borrowrecord.email=?1 and borrowrecord.book_id=book.book_id and borrowrecord.return_id=returnrecord.return_id")
    List<Map<String,Object>> selectRecordByEmail(String email);


    @Query(nativeQuery = true,value = "insert into borrowrecord(record_id,email,book_id,book_borrow_time,book_return_time) values (?1,?2,?3,?4,?5)")
    void insertRecords(Integer recordID,String email,String bookID,String bookBorrowTime,String bookReturnTime);
}
