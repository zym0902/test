package com.telchina.springboot.test.springboot.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, Long> {
    User findByUsername(String username);
}
