package com.database.test.repository;

import com.database.test.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
    @Query(nativeQuery = true,value = "select * from User where email=?1")
    List<User> selectByEmail(String email);


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "insert into user (email, username, password, gender, qq, tel, introduction) values (?1,?2,?3,?4,?5,?6,?7)")
    void insertUser(String email,String username,String password,String gender,String QQ,String tel,String introduction);


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update user set user.username=?2,user.gender=?3,user.qq=?4,user.tel=?5,user.introduction=?6 where user.email=?1")
    void updateUserInfo(String email,String username,String gender,String qq,String tel,String introduction);
}
