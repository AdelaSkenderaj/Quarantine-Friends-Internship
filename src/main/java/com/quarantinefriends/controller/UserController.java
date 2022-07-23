package com.quarantinefriends.controller;

import com.quarantinefriends.dto.UserDTO;
import com.quarantinefriends.exception.EmailExistException;
import com.quarantinefriends.exception.UserNotFoundException;
import com.quarantinefriends.exception.UsernameExistException;
import com.quarantinefriends.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO user) throws EmailExistException, UsernameExistException {
        UserDTO newUser = userService.register(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers(){
        List<UserDTO> users = this.userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId){
        UserDTO user = this.userService.getUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/reset-password/{userId}")
    public void resetPassword(@PathVariable Long userId, @RequestBody String password) throws UserNotFoundException {
        userService.resetPassword(userId, password);
    }

    @PutMapping("/forget-password/{userId}")
    public void forgetPassword(@PathVariable Long userId) throws UserNotFoundException {
        userService.forgetPassword(userId);
    }
}
