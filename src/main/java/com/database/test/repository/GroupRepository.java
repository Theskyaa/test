package com.database.test.repository;


import com.database.test.entity.GroupList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupList,Integer> {


    @Query(nativeQuery = true,value = "select grouplist.group_id,group_name,group_founder,group_foundingtime from groupList,userbelong where grouplist.group_id=userbelong.group_id and userbelong.email=?1")
    List<GroupList> selectJoinInGroupName(String email);


    @Query(nativeQuery = true,value = "select * from grouplist where group_id=?1")
    GroupList selectByGroupId(Integer groupId);
}
