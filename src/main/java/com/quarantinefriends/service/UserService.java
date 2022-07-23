package com.quarantinefriends.service;


import com.quarantinefriends.dao.RoleDao;
import com.quarantinefriends.dao.UserDao;
import com.quarantinefriends.dto.RoleDTO;
import com.quarantinefriends.dto.UserDTO;
import com.quarantinefriends.exception.EmailExistException;
import com.quarantinefriends.exception.UsernameExistException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@ComponentScan(basePackages = "com.quarantinefriends")
@NoArgsConstructor
public class UserService {

    //private PasswordEncoder passwordEncoder;

    private UserDao userDao;
    private RoleDao roleDao;


    @Autowired
    public UserService(UserDao userDao, RoleDao roleDao) {
        this.userDao = userDao;
        this.roleDao = roleDao;
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
        //String encodedPassword = encodePassword(user.getPassword());
        //newUser.setPassword(encodedPassword);
        //newUser.setPhoto("./assets/images/anonymous.png");
        newUser.setRole(role);
        newUser.setHobbies(user.getHobbies());
        System.out.println(newUser.getHobbies());
        newUser.setPreferences(user.getPreferences());
        System.out.println(user.getPreferences());
        userDao.save(newUser);
        return newUser;
    }

    /*private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }*/

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

    public UserDTO getUser(Long userId) {
        return this.userDao.findById(userId);
    }
}
