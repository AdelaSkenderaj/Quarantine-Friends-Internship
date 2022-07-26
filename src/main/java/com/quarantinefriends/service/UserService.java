package com.quarantinefriends.service;


import com.quarantinefriends.configuration.JwtUtils;
import com.quarantinefriends.dao.RoleDao;
import com.quarantinefriends.dao.UserDao;
import com.quarantinefriends.dto.*;
import com.quarantinefriends.exception.*;
import com.quarantinefriends.repository.UserRepository;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@NoArgsConstructor
public class UserService {

    private PasswordEncoder passwordEncoder;

    private UserDao userDao;
    private RoleDao roleDao;

    private AuthenticationManager authenticationManager;

    private JwtUtils jwtUtils;

    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String email;


    @Autowired
    public UserService(UserDao userDao, RoleDao roleDao, PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager, JwtUtils jwtUtils, JavaMailSender javaMailSender) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.javaMailSender = javaMailSender;
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
        newUser.setPhoto("./assets/images/anonymous.png");
        newUser.setRole(role);
        newUser.setHobbies(user.getHobbies());
        newUser.setPreferences(user.getPreferences());
        userDao.save(newUser);
        return newUser;
    }


    public ResponseEntity<LoginResponse> login(LoginDTO loginDTO) throws AccountHasBeenBannedException {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDTO user = userDao.findByUsername(loginDTO.getUsername());
        if(user.isAccountTerminated()) {
            throw new AccountHasBeenBannedException("This account has been banned");
        }
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

    public void forgotPassword(String email) throws EmailNotFoundException {
        UserDTO userDTO = userDao.findByEmail(email);
        if(userDTO == null) {
            throw new EmailNotFoundException("Email not found");
        }


        //Generate new random password and send the user an email with this password
        String newPassword = RandomStringUtils.randomAlphanumeric(15);
        sendEmail(userDTO,newPassword);
        String encodedPassword = encodePassword(newPassword);
        System.out.println("New password is " + newPassword);
        userDTO.setPassword(encodedPassword);

        //TODO:Send new password in email

        userDao.save(userDTO);
    }


    public void sendEmail(UserDTO user, String password){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(email);
        mail.setSubject("A new password was set for your account");
        mail.setText("Hi " + user.getFirstName() + " " + user.getLastName()  +
                "!\n\nYou can use this password to access your account.\n\n" +
                "You are advised to change your password after signin in.\n" +
                "password: " + password);

        javaMailSender.send(mail);
    }

    public ResponseEntity<LoginResponse> updateUser(Long userId, UserDTO newUser) throws UserNotFoundException, EmailExistException, UsernameExistException {
        UserDTO oldUser = userDao.findById(userId);
        boolean same = oldUser.getUsername().equals(newUser.getUsername());
        validateNewUsernameAndEmail(oldUser.getUsername(), newUser.getUsername(), newUser.getEmail());
        oldUser.setFirstName(newUser.getFirstName());
        oldUser.setLastName(newUser.getLastName());
        oldUser.setUsername(newUser.getUsername());
        oldUser.setEmail(newUser.getEmail());
        oldUser.setHobbies(newUser.getHobbies());
        oldUser.setPreferences(newUser.getPreferences());

        userDao.save(oldUser);

        HttpHeaders responseHeaders = new HttpHeaders();
        LoginResponse loginResponse = new LoginResponse(oldUser);

        if (!same) {
            String jwt = jwtUtils.generateJwtToken(newUser.getUsername());
            responseHeaders.set("jwtToken", jwt);
            loginResponse.setToken(jwt);

        }

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(loginResponse);
    }

    public UserDTO validateNewUsernameAndEmail(String currentUsername, String newUserName, String newEmail) throws UsernameExistException, EmailExistException, UserNotFoundException {
        UserDTO userByNewUsername = userDao.findByUsername(newUserName);
        UserDTO userByNewEmail = userDao.findByEmail(newEmail);
        if (StringUtils.isNotEmpty(currentUsername)) {
            UserDTO currentUser = userDao.findByUsername(currentUsername);
            if (currentUser == null) {
                throw new UserNotFoundException("No user found by Username " + currentUsername);
            }
            if (userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
                throw new UsernameExistException("Username already exist");
            }
            if (userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
                throw new EmailExistException("Email already exist");
            }
            return currentUser;
        } else {
            UserDTO userByUsername = userDao.findByUsername(newUserName);
            if (userByUsername != null) {
                throw new UsernameExistException("Username already exist");
            }
            UserDTO userByEmail = userDao.findByEmail(newUserName);
            if (userByEmail != null) {
                throw new UsernameExistException("Username already exist");
            }
            return null;
        }
    }

    public void removeMatch(Long friendId, UserDTO userDTO) throws UserNotFoundException {
        userDao.removeFriend(friendId, userDTO.getId());
    }

    public List<UserDTO> getFriendsByUserId(Long userId) throws UserNotFoundException {
        return userDao.getFriendsByUserId(userId);
    }

    public void blockUser(UserDTO userDTO, Long blockUserId) throws UserNotFoundException {
        userDao.blockUser(userDTO.getId(), blockUserId);
    }

    public List<UserDTO> getBlockedUsersByUserId(Long userId) {
        return userDao.getBlockedUsersByUserId(userId);
    }

    public void unblockUser(UserDTO userDTO, Long blockedUserId) throws UserNotFoundException {
        userDao.unblockUser(userDTO.getId(), blockedUserId);
    }

    public void terminateAccount(Long userId, UserDTO userDTO) throws UserNotFoundException {
        userDao.terminateAccount(userId, userDTO);
    }

    public void revokeBan(Long userId, UserDTO userDTO) throws UserNotFoundException {
        UserDTO user = userDao.findById(userId);
        user.setAccountTerminated(false);
        userDao.save(user);
    }

    public List<MatchDTO> getMatches(Long userId) throws UserNotFoundException {
        List<UserDTO> availableUsers = userDao.getAvailableUsersForMatch(userId);
        List<MatchDTO> matches = new ArrayList<>();
        double matchingPercentage = 0;

        for(UserDTO user : availableUsers) {
            MatchDTO match = new MatchDTO();
            UserDTO loggedInUser = userDao.findById(userId);
            matchingPercentage = calculateMatchingPercentage(loggedInUser, user);

            if(matchingPercentage > 0) {
                match.setUser(user);
                match.setMatchingPercentage((int) (matchingPercentage * 100));
                matches.add(match);
            }
        }
        return matches;
    }

    private double calculateMatchingPercentage(UserDTO loggedInUser, UserDTO matchedUser) {
        double hobbyMatch = 0;
        double preferenceMatch = 0;
        List<HobbyDTO> matchedUserHobbies = matchedUser.getHobbies();
        for(HobbyDTO hobby : loggedInUser.getHobbies()) {
            if(matchedUserHobbies.contains(hobby)) {
                hobbyMatch += 1;
            }
        }
        if(!loggedInUser.getHobbies().isEmpty()) {
            hobbyMatch /= loggedInUser.getHobbies().size();
        }

        for(PreferenceDTO preference : loggedInUser.getPreferences()) {
            if(matchedUser.getPreferences().contains(preference)) {
                preferenceMatch += 1;
            }
        }
        if(!loggedInUser.getPreferences().isEmpty()) {
            preferenceMatch /= loggedInUser.getPreferences().size();
        }

        double ageMatch = 0.2 - (double)Math.min(10, Math.abs(loggedInUser.getAge() - matchedUser.getAge()))/10;

        return ((hobbyMatch * 0.4) + (preferenceMatch * 0.4) + ageMatch);
    }


    public List<UserDTO> getEnabledUsers() {
        return this.userDao.getEnabledUsers();
    }

    public List<UserDTO> getBannedUsers() {
        return userDao.getBannedUsers();
    }


    public boolean checkIfFriends(Long userId, Long friendId) throws UserNotFoundException {
        return this.userDao.checkIfFriends(userId, friendId);
    }
}
