package com.quarantinefriends.service;


import com.quarantinefriends.configuration.JwtUtils;
import com.quarantinefriends.dao.RoleDao;
import com.quarantinefriends.dao.UserDao;
import com.quarantinefriends.dto.LoginDTO;
import com.quarantinefriends.dto.LoginResponse;
import com.quarantinefriends.dto.RoleDTO;
import com.quarantinefriends.dto.UserDTO;
import com.quarantinefriends.exception.EmailExistException;
import com.quarantinefriends.exception.UserNotFoundException;
import com.quarantinefriends.exception.UsernameExistException;
import com.quarantinefriends.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@NoArgsConstructor
public class UserService {

    private PasswordEncoder passwordEncoder;

    private UserDao userDao;
    private RoleDao roleDao;

    private AuthenticationManager authenticationManager;

    private JwtUtils jwtUtils;
    private UserRepository userRepository;


    @Autowired
    public UserService(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtUtils jwtUtils, UserRepository userRepository) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }


    public UserDTO register(UserDTO user) throws EmailExistException, UsernameExistException {
        validateUsernameAndEmail(user.getUsername(), user.getEmail());

        UserDTO newUser = new UserDTO();
        RoleDTO role = new RoleDTO();
        if(user.getRole() != null) {
            role = this.roleDao.findById(role.getId());
        }
        else {
            role = roleDao.findById(1L);
        }

        newUser.setFirstName(user.getFirstName());
        newUser.setLastName(user.getLastName());
        newUser.setUsername(user.getUsername());
        newUser.setAge(user.getAge());
        newUser.setEmail(user.getEmail());
        String encodedPassword = encodePassword(user.getPassword());
        newUser.setPassword(encodedPassword);
        //newUser.setPhoto("./assets/images/anonymous.png");
        newUser.setRole(role);
        newUser.setHobbies(user.getHobbies());
        System.out.println(newUser.getHobbies());
        newUser.setPreferences(user.getPreferences());
        System.out.println(user.getPreferences());
        userDao.save(newUser);
        return newUser;
    }


    public ResponseEntity<LoginResponse> login(LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDTO user = userDao.findByUsername(loginDTO.getUsername());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("jwtToken", jwt);
        LoginResponse loginResponse = new LoginResponse(user, jwt);
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(loginResponse);
    }


    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

    private void validateUsernameAndEmail(String username, String email) throws UsernameExistException, EmailExistException {
        if(userDao.findByUsername(username) != null) {
            throw new UsernameExistException("Username already exists");
        }

        if(userDao.findByEmail(email) != null) {
            throw new EmailExistException("Email already exists");
        }

    }

    public List<UserDTO> getUsers() {
        return this.userDao.findAll();
    }

    public UserDTO getUser(Long userId) throws UserNotFoundException {
        return this.userDao.findById(userId);
    }

    public void resetPassword(Long userId, String password) throws UserNotFoundException {
        UserDTO userDTO = userDao.findById(userId);
        userDTO.setPassword(encodePassword(password));
        userDao.save(userDTO);
    }

    public void forgetPassword(String email) throws UserNotFoundException {
        UserDTO userDTO = userDao.findByEmail(email);

        //Generate new random password and send the user an email with this password
        String newPassword = RandomStringUtils.randomAlphanumeric(15);
        String encodedPassword = encodePassword(newPassword);
        System.out.println("New password is " + newPassword);
        userDTO.setPassword(encodedPassword);

        //TODO:Send new password in email

        userDao.save(userDTO);
    }

    public void removeMatch(Long friendId, UserDTO userDTO) throws UserNotFoundException {
        userDao.removeFriend(friendId, userDTO.getId());
    }

    public List<UserDTO> getFriendsByUserId(Long userId) throws UserNotFoundException {
        return userDao.getFriendsByUserId(userId);
    }

    public void blockUser(Long userId, Long blockUserId) throws UserNotFoundException {
        //TODO:if the user was a friend remove from friends list
        userDao.blockUser(userId, blockUserId);
    }

    public List<UserDTO> getBlockedUsersByUserId(Long userId) {
        return userDao.getBlockedUsersByUserId(userId);
    }

    public void unblockUser(Long userId, Long blockedUserId) throws UserNotFoundException {
        userDao.unblockUser(userId, blockedUserId);
    }

    public void terminateAccount(Long userId) throws UserNotFoundException {
        UserDTO user = userDao.findById(userId);
        user.setAccountTerminated(true);
        userDao.save(user);
    }


}
