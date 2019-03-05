package com.telchina.springboot.test.springboot;

import com.telchina.springboot.test.springboot.config.JdbcTemplateTest;
import com.telchina.springboot.test.springboot.pojo.BlogProperties;
import com.telchina.springboot.test.springboot.pojo.Person;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootTestApplicationTests {

    @Autowired
    private RedisTemplate<String, Person> redisTemplate;

    @Test
    public void test2() throws Exception {

        // 保存对象
        Person person = new Person("超人", 20);
        redisTemplate.opsForValue().set(person.getUsername(), person);

        person = new Person("蝙蝠侠", 30);
        redisTemplate.opsForValue().set(person.getUsername(), person);

        person = new Person("蜘蛛侠", 40);
        redisTemplate.opsForValue().set(person.getUsername(), person);

        Assert.assertEquals(20, redisTemplate.opsForValue().get("超人").getAge().longValue());
        Assert.assertEquals(30, redisTemplate.opsForValue().get("蝙蝠侠").getAge().longValue());
        Assert.assertEquals(40, redisTemplate.opsForValue().get("蜘蛛侠").getAge().longValue());

    }


    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void test1(){
        stringRedisTemplate.opsForValue().set("111","11");
        Assert.assertEquals("11",stringRedisTemplate.opsForValue().get("111"));
    }



    private final static Logger log = LoggerFactory.getLogger(SpringBootTestApplication.class);

    @Autowired
    private BlogProperties blogProperties;

    @Test
    public void test(){
        log.info(blogProperties.getDesc());
    }
}

