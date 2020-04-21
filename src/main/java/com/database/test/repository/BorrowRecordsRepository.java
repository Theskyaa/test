package com.database.test.repository;

import com.database.test.entity.BorrowRecords;
import com.database.test.entity.GroupList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

public interface BorrowRecordsRepository extends JpaRepository<BorrowRecords,Integer> {

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "insert into borrowrecord (record_id, email, book_id, borrow_time, return_time) values (?1,?2,?3,?4,?5)")
    void insertRecord(Integer recordId,String email,Integer bookId,String borrowTime,String returnTime);

    @Query(nativeQuery = true,value = "select * from borrowrecord order by record_id desc limit 1")
    List<BorrowRecords> selectMaxRecordId();

    @Query(nativeQuery = true,value = "select * from grouplist order by group_id desc limit 1")
    GroupList selectMaxGroupId();

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update borrowrecord set return_time=?2 where book_id=?1 and return_time='null'")
    void updateReturnTimeByBookId(Integer bookId,String returnTime);

    @Query(nativeQuery = true,value = "select record_id, book_name, borrow_time, return_time from borrowrecord,bookbelong,book where email=?1 and bookbelong.group_id=?2 and return_time<>'null' and book.book_id=bookbelong.book_id and borrowrecord.book_id=book.book_id")
    List<Map<String,Object>> selectBorrowRecordsByEmailAndGroupId(String email,Integer groupId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "delete from borrowrecord where book_id in (select borrowrecord.book_id from bookbelong,book,borrowrecord where group_id=?1 and bookbelong.book_id=book.book_id and book.book_id=borrowrecord.book_id)")
    void deleteByGroupIdFromBorrowRecord(Integer groupId);

}
