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
        }
        return chatDTO;
    }

    public static Chat mapToEntity(ChatDTO chatDTO) {
        Chat chat = new Chat();
        if(chatDTO != null) {
            chat.setId(chatDTO.getId());
        }
        return chat;
    }
}
