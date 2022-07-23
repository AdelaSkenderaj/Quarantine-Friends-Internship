package com.quarantinefriends.dao;

import com.quarantinefriends.dto.UserDTO;
import com.quarantinefriends.entity.User;
import com.quarantinefriends.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Component
public class UserDao {

    private UserRepository userRepository;

    @Autowired
    public UserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static UserDTO mapToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        if(user != null) {
            userDTO.setId(user.getId());
            userDTO.setFirstName(user.getFirstName());
            userDTO.setLastName(user.getLastName());
            userDTO.setUsername(user.getUsername());
            userDTO.setAge(user.getAge());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            userDTO.setPhoto(user.getPhoto());
            if(user.getRole() != null) {
                userDTO.setRole(RoleDao.mapToDTO(user.getRole()));
            }
            if(user.getHobbies() != null) {
                userDTO.setHobbies(user.getHobbies().stream().map(HobbyDao::mapToDTO).collect(Collectors.toList()));
            }
            if(user.getPreferences() != null) {
                userDTO.setPreferences(user.getPreferences().stream().map(PreferenceDao::mapToDTO).collect(Collectors.toList()));
            }
            userDTO.setAccountTerminated(user.isAccountTerminated());
        }
        return userDTO;
    }

    public static User mapToEntity(UserDTO userDTO) {
        User user = new User();
        if(userDTO != null) {
            user.setId(userDTO.getId());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setUsername(userDTO.getUsername());
            user.setAge(userDTO.getAge());
            user.setEmail(userDTO.getEmail());
            user.setPassword(userDTO.getPassword());
            user.setPhoto(userDTO.getPhoto());
            if(userDTO.getRole() != null) {
                user.setRole(RoleDao.mapToEntity(userDTO.getRole()));
            }
            if(userDTO.getHobbies() != null) {
                user.setHobbies(userDTO.getHobbies().stream().map(HobbyDao::mapToEntity).collect(Collectors.toList()));
            }
            if(userDTO.getPreferences() != null) {
                user.setPreferences(userDTO.getPreferences().stream().map(PreferenceDao::mapToEntity).collect(Collectors.toList()));
            }
            user.setAccountTerminated(userDTO.isAccountTerminated());
        }
        return user;
    }

    public void save(UserDTO newUser) {
        this.userRepository.save(mapToEntity(newUser));
    }

    public UserDTO findByUsername(String username) {
        if (this.userRepository.findByUsername(username) == null){
            return null;
        }
        return UserDao.mapToDTO(this.userRepository.findByUsername(username));
    }

    public UserDTO findByEmail(String email) {
        if(userRepository.findByEmail(email) == null) {
            return null;
        }
        return UserDao.mapToDTO(userRepository.findByEmail(email));
    }

    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map(UserDao::mapToDTO).collect(Collectors.toList());
    }

    public UserDTO findById(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(UserDao::mapToDTO).orElse(null);
    }
}
