package com.database.test.repository;


import com.database.test.entity.GroupList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface GroupRepository extends JpaRepository<GroupList,Integer> {


    @Query(nativeQuery = true,value = "select grouplist.group_id,group_name,group_founder,group_foundingtime,group_introduction,group_number from groupList,userbelong where grouplist.group_id=userbelong.group_id and userbelong.email=?1")
    List<GroupList> selectJoinInGroup(String email);


    @Query(nativeQuery = true,value = "select * from grouplist where group_id=?1")
    GroupList selectByGroupId(Integer groupId);


    @Query(nativeQuery = true,value = "select * from grouplist where group_id not in (select group_id from userbelong where email=?1) ")
    List<GroupList> selectNotJoinInGroupByEmial(String email);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "insert into userbelong (email, group_id) values (?1,?2)")
    int insertUserBelong(String email,Integer groupId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "delete from userbelong where userbelong.email=?1 and userbelong.group_id=?2")
    int deleteByGroupIdAndEmail(String email,Integer groupId);

    @Query(nativeQuery = true,value = "select * from grouplist order by group_id desc limit 1")
    GroupList selectMaxGroupId();

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update grouplist set group_number=?1 where group_id=?1")
    int updateGroupNumber(Integer num);

    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "insert into grouplist (group_id, group_name, group_founder, group_foundingtime, group_introduction, group_number) values (?1,?2,?3,?4,?5,?6)")
    int insertGroupListRecord(Integer groupId,String groupName,String email,String time,String introduction,Integer num);

    @Query(nativeQuery = true,value = "select * from grouplist where group_name like concat('%',?1,'%') and group_id not in (select userbelong.group_id from userbelong where email=?2)")
    List<GroupList> selectGroupLike(String groupName,String email);
}
