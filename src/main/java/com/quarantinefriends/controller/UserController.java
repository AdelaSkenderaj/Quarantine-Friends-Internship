package com.quarantinefriends.controller;

import com.quarantinefriends.dto.LoginDTO;
import com.quarantinefriends.dto.LoginResponse;
import com.quarantinefriends.dto.MatchDTO;
import com.quarantinefriends.dto.UserDTO;
import com.quarantinefriends.exception.*;
import com.quarantinefriends.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/")
@CrossOrigin("http://localhost:4200")
public class UserController extends ExceptionHandling {

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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDTO) throws AccountHasBeenBannedException {
        return this.userService.login(loginDTO);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getUsers(){
        List<UserDTO> users = this.userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long userId) throws UserNotFoundException {
        UserDTO user = this.userService.getUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/enabled-users")
    public List<UserDTO> getEnabledUsers() {
        return this.userService.getEnabledUsers();
    }

    @PutMapping("/reset-password/{userId}")
    public void resetPassword(@PathVariable Long userId, @RequestBody String password) throws UserNotFoundException {
        userService.resetPassword(userId, password);
    }

    @PutMapping("/forgot-password")
    public void forgotPassword(@RequestBody String email) throws EmailNotFoundException {
        userService.forgotPassword(email);
    }

    @PutMapping("/user/{userId}")
    public ResponseEntity<LoginResponse> updateUser(@PathVariable Long userId, @RequestBody UserDTO userDTO) throws UserNotFoundException, EmailExistException, UsernameExistException {
        return this.userService.updateUser(userId, userDTO);
    }

    @PutMapping("/unmatch/{friendId}")
    public void removeMatch(@PathVariable Long friendId, @RequestBody UserDTO userDTO) throws UserNotFoundException {
        userService.removeMatch(friendId, userDTO);
    }

    @GetMapping("/user/{userId}/friends")
    public List<UserDTO> getFriendsByUserId(@PathVariable Long userId) throws UserNotFoundException {
        return userService.getFriendsByUserId(userId);
    }

    @PutMapping("/user/block/{blockUserId}")
    public void blockUser(@RequestBody UserDTO userDTO, @PathVariable Long blockUserId) throws UserNotFoundException {
        userService.blockUser(userDTO, blockUserId);
    }

    @GetMapping("/user/{userId}/blocked-users")
    public List<UserDTO> getBlockedUsersByUserId(@PathVariable Long userId) {
        return userService.getBlockedUsersByUserId(userId);
    }

    @PutMapping("/user/unblock/{blockedUserId}")
    public void unblockUser(@RequestBody UserDTO userDTO, @PathVariable Long blockedUserId) throws UserNotFoundException {
        userService.unblockUser(userDTO, blockedUserId);
    }

    @PutMapping("/terminate/{userId}")
    public void terminateAccount(@PathVariable Long userId, @RequestBody UserDTO userDTO) throws UserNotFoundException {
        userService.terminateAccount(userId, userDTO);
    }

    @PutMapping("/revoke/{userId}")
    public void revokeBan(@PathVariable Long userId, @RequestBody UserDTO userDTO) throws UserNotFoundException {
        userService.revokeBan(userId, userDTO);
    }

    @GetMapping("/matches/{userId}")
    public List<MatchDTO> getMatches(@PathVariable Long userId) throws UserNotFoundException {
        return userService.getMatches(userId);
    }

    @GetMapping("/banned-users")
    public List<UserDTO> getBannedUsers() {
        return userService.getBannedUsers();
    }


    @GetMapping("/areFriends/{userId}/{friendId}")
    public boolean checkIfFriends(@PathVariable Long userId, @PathVariable Long friendId) throws UserNotFoundException {
        return this.userService.checkIfFriends(userId, friendId);
    }
}
