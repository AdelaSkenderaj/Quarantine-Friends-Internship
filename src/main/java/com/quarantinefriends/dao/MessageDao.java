package com.quarantinefriends.dao;

import com.quarantinefriends.dto.MessageDTO;
import com.quarantinefriends.entity.Message;
import org.springframework.stereotype.Component;

@Component
public class MessageDao {

    public static MessageDTO mapToDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        if(message!=null) {
            messageDTO.setId(message.getId());
            messageDTO.setMessage(message.getMessage());
            messageDTO.setUser(UserDao.mapToDTO(message.getUser()));
            messageDTO.setChat(ChatDao.mapToDTO(message.getChat()));
        }
        return messageDTO;
    }

    public static Message mapToEntity(MessageDTO messageDTO) {
        Message message = new Message();
        if(messageDTO!=null) {
            message.setId(messageDTO.getId());
            message.setMessage(messageDTO.getMessage());
            message.setUser(UserDao.mapToEntity(messageDTO.getUser()));
            message.setChat(ChatDao.mapToEntity(messageDTO.getChat()));
        }
        return message;
    }
}
