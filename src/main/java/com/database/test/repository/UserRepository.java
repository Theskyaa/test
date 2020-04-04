package com.database.test.repository;

import com.database.test.entity.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {
    @Query(nativeQuery = true,value = "select * from Users where email=?1")
    User selectByEmail(String email);

    List<User> findByEmail(String email);

}
