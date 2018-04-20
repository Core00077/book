package com.corechan.book.dao;

import com.corechan.book.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {
    private final RedisTemplate<String,Object> objectRedisTemplate;

    @Autowired
    public UserDao(RedisTemplate<String, Object> objectRedisTemplate) {
        this.objectRedisTemplate = objectRedisTemplate;
    }

    public void save(User user){
        objectRedisTemplate.opsForValue().set(user.getUserId(),user);
    }

    public User findById(String id){
        return (User) objectRedisTemplate.opsForValue().get(id);
    }
}
