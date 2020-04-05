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
    @Query(nativeQuery = true,value = "insert into user (email, username, password) values (?1,?2,?3)")
    void insertUser(String email,String username,String password);

}
