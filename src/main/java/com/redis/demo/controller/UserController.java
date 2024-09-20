package com.redis.demo.controller;

import com.redis.demo.entity.User;
import com.redis.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }


  @GetMapping("")
  public ResponseEntity<?> getAllUsers() {
    List<User> users = userService.getUsers();
    return ResponseEntity.ok(users);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<?> getUserById(@PathVariable Long userId) {
    User userById = userService.getUserById(userId);
    return ResponseEntity.ok(userById);
  }

  @PostMapping("/save")
  public ResponseEntity<?> createUser(@RequestBody User user) {
    User saveUser = userService.saveUser(user);
    return ResponseEntity.ok(saveUser);
  }

}
