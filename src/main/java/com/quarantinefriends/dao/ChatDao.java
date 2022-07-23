package com.quarantinefriends.dao;

import com.quarantinefriends.dto.ChatDTO;
import com.quarantinefriends.entity.Chat;
import org.springframework.stereotype.Component;

@Component
public class ChatDao {

    public static ChatDTO mapToDTO(Chat chat) {
        ChatDTO chatDTO = new ChatDTO();
        if(chat != null) {
            chatDTO.setId(chat.getId());
            chatDTO.setFriendship(FriendshipDao.mapToDTO(chat.getFriendship()));
        }
        return chatDTO;
    }

    public static Chat mapToEntity(ChatDTO chatDTO) {
        Chat chat = new Chat();
        if(chatDTO != null) {
            chat.setId(chatDTO.getId());
            chat.setFriendship(FriendshipDao.mapToEntity(chatDTO.getFriendship()));
        }
        return chat;
    }
}
