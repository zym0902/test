package com.telchina.springboot.test.springboot.mongoDb;

import com.telchina.springboot.test.springboot.mongodb.User;
import com.telchina.springboot.test.springboot.mongodb.UserRepo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDBTest {

    @Autowired
    private UserRepo userRepo;

    @Before
    public void setUp(){
        userRepo.deleteAll();
    }

    @Test
    public void test(){
        userRepo.save(new User("aaa","didi",20));
        userRepo.save(new User("bbb","meimei",30));
        userRepo.save(new User("ccc","gege",40));

        User u1 = userRepo.findByUsername("didi");
        userRepo.delete(u1);
        Assert.assertEquals(2,userRepo.findAll().size());

        User u2 = userRepo.findByUsername("meimei");
        userRepo.delete(u2);
        Assert.assertEquals(1,userRepo.findAll().size());




    }







}
