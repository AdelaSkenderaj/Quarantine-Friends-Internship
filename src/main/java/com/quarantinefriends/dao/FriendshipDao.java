package com.quarantinefriends.dao;

import com.quarantinefriends.dto.FriendshipDTO;
import com.quarantinefriends.entity.Friendship;
import org.springframework.stereotype.Component;

@Component
public class FriendshipDao {

    public static FriendshipDTO mapToDTO(Friendship friendship) {
        FriendshipDTO friendshipDTO = new FriendshipDTO();
        if(friendship != null) {
            friendshipDTO.setId(friendship.getId());
            friendshipDTO.setFriendOne(UserDao.mapToDTO(friendship.getFriendOne()));
            friendshipDTO.setFriendTwo(UserDao.mapToDTO(friendship.getFriendTwo()));
        }
        return friendshipDTO;
    }

    public static Friendship mapToEntity(FriendshipDTO friendshipDTO) {
        Friendship friendship = new Friendship();
        if(friendshipDTO != null) {
            friendship.setId(friendshipDTO.getId());
            friendship.setFriendOne(UserDao.mapToEntity(friendshipDTO.getFriendOne()));
            friendship.setFriendTwo(UserDao.mapToEntity(friendshipDTO.getFriendTwo()));
        }
        return friendship;
    }

}
