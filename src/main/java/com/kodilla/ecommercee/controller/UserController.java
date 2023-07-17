package com.kodilla.ecommercee.controller;

import com.kodilla.ecommercee.domain.User;
import com.kodilla.ecommercee.domain.dto.UserDto;
import com.kodilla.ecommercee.mapper.UserMapper;
import com.kodilla.ecommercee.repository.UserRepository;
import com.kodilla.ecommercee.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Random;

@RestController
        @RequestMapping("/ecommercee/users")
    @RequiredArgsConstructor
    public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
        User user = userMapper.mapToUser(userDto);
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "{userId}")
    public ResponseEntity<Void> banUser(@PathVariable Long userId)  {
        User banedUser =userService.findUser(userId);
        banedUser.setActive(false);
        userService.saveUser(banedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{userId}")
    public ResponseEntity<Integer> generateKey(@PathVariable Long userId) {
        User activeUser = userService.findUser(userId);
        activeUser.setExpiryDate(LocalDateTime.now());
        activeUser.setKey(new Random().nextInt(99999999));
        activeUser.setActive(true);
        userService.saveUser(activeUser);
        return ResponseEntity.ok(userService.findUser(userId).getKey());
    }
}