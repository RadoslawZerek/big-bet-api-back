package com.radoslawzerek.bigbetbackend.controllers;

import com.radoslawzerek.bigbetbackend.dto.UserDto;
import com.radoslawzerek.bigbetbackend.dto.UserDtoList;
import com.radoslawzerek.bigbetbackend.mapper.UserMapper;
import com.radoslawzerek.bigbetbackend.pojo.LogInFeedback;
import com.radoslawzerek.bigbetbackend.pojo.SignUpFeedback;
import com.radoslawzerek.bigbetbackend.service.UserService;
import com.radoslawzerek.bigbetbackend.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/bigbet")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService service;
    private final UserMapper mapper;

    @Autowired
    public UserController(UserService service, UserMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/users/{login}/{password}")
    public LogInFeedback logUserIn(@PathVariable String login, @PathVariable String password) {
        return service.logUserIn(login, password);
    }


    @PostMapping("/users")
    public SignUpFeedback signUserUp(@RequestBody UserDto user) {
        return service.signUserUp(mapper.mapToUser(user));
    }

    @GetMapping("/users/{userId}")
    public UserDto getUserById(@PathVariable Long userId) throws UserNotFoundException {
        return mapper.mapToUserDto(service.getUserById(userId));
    }

    @GetMapping("/users")
    public UserDtoList getAllUsers() {
        return new UserDtoList(mapper.mapToUserDtoList(service.getAllUsers()));
    }

    @PatchMapping("/users")
    public UserDto updateUser(@RequestBody UserDto user) throws UserNotFoundException {
        return mapper.mapToUserDto(service.updateUser(mapper.mapToUser(user)));
    }

    @PatchMapping("/users/{userId}")
    public UserDto updateUserPassword(@PathVariable Long userId, @RequestBody String newPassword) throws UserNotFoundException {
        return mapper.mapToUserDto(service.updateUserPassword(userId, newPassword));
    }

    @GetMapping("/users/check/{login}")
    public Boolean checkIfUserExists(@PathVariable String login) {
        return service.checkIfUserExists(login);
    }

    @DeleteMapping("/users/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        service.deleteUser(userId);
    }
}
