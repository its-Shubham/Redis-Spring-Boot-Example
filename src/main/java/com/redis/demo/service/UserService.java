package com.redis.demo.service;

import com.redis.demo.entity.User;
import com.redis.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final RedisService redisService;

  public UserService(UserRepository userRepository, RedisService redisService) {
    this.userRepository = userRepository;
    this.redisService = redisService;
  }

  // Retrieve a user by ID, first checking the cache, then the database
  public User getUserById(Long userId) {
    User cachedUser = redisService.get(String.valueOf(userId), User.class);
    if (cachedUser != null) {
      return cachedUser;
    }
    User user = userRepository.findById(userId).orElse(null);
    if (user != null) {
      redisService.set(String.valueOf(userId), user, 30L);
    }
    return user;
  }

  // Save a user to the database
  public User saveUser(User user) {
    User savedUser = userRepository.save(user);
    // Update cache after saving to the database
    redisService.set(String.valueOf(savedUser.getId()), savedUser, 30L);
    return savedUser;
  }

  // Retrieve all users, first checking the cache, then the database
  public List<User> getUsers() {
//    List<User> users = redisService.getList("ALL_USERS", User.class);
//    if (users != null && !users.isEmpty()) {
//      return users;
//    }
    List<User> allUsers = userRepository.findAll();
    redisService.set("ALL_USERS", allUsers, 30L);
    return allUsers;
  }
}
