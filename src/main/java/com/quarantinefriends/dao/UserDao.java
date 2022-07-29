package com.quarantinefriends.dao;

import com.quarantinefriends.dto.UserDTO;
import com.quarantinefriends.entity.User;
import com.quarantinefriends.exception.UserNotFoundException;
import com.quarantinefriends.repository.UserRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
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

    @Transactional
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

    public UserDTO findById(Long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            throw new UserNotFoundException();
        }
        return UserDao.mapToDTO(user);
    }

    //TODO: Place methods in service

    public void addFriend(Long fromUser, Long toUser) throws UserNotFoundException {
        User friendOne = userRepository.findById(fromUser).orElse(null);
        User friendTwo = userRepository.findById(toUser).orElse(null);

        if (friendOne == null || friendTwo == null) {
            throw new UserNotFoundException();
        }
        friendOne.getFriends().add(friendTwo);
        friendTwo.getFriends().add(friendOne);
        userRepository.save(friendOne);
        userRepository.save(friendTwo);
    }

    public void removeFriend(Long friendId, Long userId) throws UserNotFoundException {

        User friendOne = userRepository.findById(friendId).orElse(null);
        User friendTwo = userRepository.findById(userId).orElse(null);
        if(friendOne == null || friendTwo == null) {
            throw new UserNotFoundException();
        }

        friendOne.getFriends().remove(friendTwo);
        friendTwo.getFriends().remove(friendOne);
        userRepository.save(friendOne);
        userRepository.save(friendTwo);
    }

    public List<UserDTO> getFriendsByUserId(Long userId) throws UserNotFoundException {

        User user = userRepository.findById(userId).orElse(null);
        if(user == null) {
            throw new UserNotFoundException();
        }

        List<User> friends = user.getFriends();
        return friends.stream().map(UserDao::mapToDTO).collect(Collectors.toList());
    }

    public void blockUser(Long userId, Long blockUserId) throws UserNotFoundException {

        User user = userRepository.findById(userId).orElse(null);
        User blockedUser = userRepository.findById(blockUserId).orElse(null);

        if(user == null || blockedUser == null) {
            throw new UserNotFoundException();
        }

        if(user.getFriends().contains(blockedUser)) {
            removeFriend(userId, blockUserId);
        }
        user.getBlockedUsers().add(blockedUser);
        userRepository.save(user);
    }

    public List<UserDTO> getBlockedUsersByUserId(Long userId) {

        User user = userRepository.findById(userId).orElse(null);
        if(user != null) {
            return user.getBlockedUsers().stream().map(UserDao::mapToDTO).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    public void unblockUser(Long userId, Long blockedUserId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElse(null);
        User blockedUser = userRepository.findById(blockedUserId).orElse(null);

        if(user == null || blockedUser == null) {
            throw new UserNotFoundException();
        }

        user.getBlockedUsers().remove(blockedUser);
        userRepository.save(user);
    }

    public List<UserDTO> getAvailableUsersForMatch(Long userId) {
        List<UserDTO> availableUsers = userRepository.findEnabledUsers().stream().map(UserDao::mapToDTO).collect(Collectors.toList());

        User currentUser = userRepository.findById(userId).orElse(null);
        if(!availableUsers.isEmpty() && currentUser != null) {
            List<UserDTO> friends = currentUser.getFriends().stream().map(UserDao::mapToDTO).collect(Collectors.toList());
            List<UserDTO> blockedUsers = currentUser.getBlockedUsers().stream().map(UserDao :: mapToDTO).collect(Collectors.toList());
            List<UserDTO> requestedUsers = userRepository.findRequestedUsers(userId).stream().map(UserDao::mapToDTO).collect(Collectors.toList());
            List<UserDTO> blockingUsers = userRepository.findBlockingUsers(userId).stream().map(UserDao::mapToDTO).collect(Collectors.toList());
            for(UserDTO friend : friends) {
                if(availableUsers.contains(friend)) {
                    availableUsers.remove(friend);
                }
            }

            for(UserDTO blocked : blockedUsers) {
                if(availableUsers.contains(blocked)) {
                    availableUsers.remove(blocked);
                }
            }

            for(UserDTO requestedUser: requestedUsers) {
                if(availableUsers.contains(requestedUser)) {
                    availableUsers.remove(requestedUser);
                }
            }

            for(UserDTO user : blockingUsers) {
                availableUsers.remove(user);
            }
            availableUsers.remove(mapToDTO(currentUser));
            return availableUsers;
        }
        return null;
    }


    public Long getNrUsers() {
        return userRepository.count();
    }

    public Long getNrBannedUsers() {
        return userRepository.findNrBannedUsers();
    }

    public Long getNrFriendships() {
        return userRepository.findNrOfFriendships();
    }

    public List<UserDTO> getEnabledUsers() {
        return this.userRepository.findEnabledUsers().stream().map(UserDao::mapToDTO).collect(Collectors.toList());
    }

    public List<UserDTO> getBannedUsers() {
        return userRepository.findBannedUsers().stream().map(UserDao::mapToDTO).collect(Collectors.toList());
    }

    @Transactional
    public void terminateAccount(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId).orElse(null);
        if(user != null) {
            userRepository.removeFromFriendships(userId);
            userRepository.removeFromBlockedUsers(userId);
            user.setAccountTerminated(true);
            userRepository.save(user);

        }

    }

    public boolean checkIfFriends(Long userId, Long friendId) throws UserNotFoundException {
        User user = userRepository.findById(userId).orElse(null);
        UserDTO friend = this.findById(friendId);
        if(user == null) {
           throw new UserNotFoundException();
        }
        List<UserDTO> friends = user.getFriends().stream().map(UserDao::mapToDTO).collect(Collectors.toList());
        return friends.contains(friend);
    }
}
