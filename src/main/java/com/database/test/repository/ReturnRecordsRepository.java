package com.database.test.repository;

import com.database.test.entity.ReturnRecords;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;


import javax.transaction.Transactional;
import java.util.List;

public interface ReturnRecordsRepository extends Repository<ReturnRecords,Integer> {



    @Query(nativeQuery = true,value = "select * from returnrecord")
    List<ReturnRecords> listAllReturnRecords();


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "insert into returnrecord(return_id, return_time) values (?1,?2)")
    int insertReturnRecord(Integer returnId,String returnTime);

}
