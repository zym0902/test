package com.telchina.springboot.test.springboot.repo;

import com.telchina.springboot.test.springboot.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<User,String> {

    User findByName(String name);

    User findByNameAndAge(String name,Integer age);

    @Query("from User u where u.name=?1")
    User findUser(@Param("name")String name);
}
