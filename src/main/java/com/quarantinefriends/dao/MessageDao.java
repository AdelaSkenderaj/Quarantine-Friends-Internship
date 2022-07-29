package com.quarantinefriends.dao;

import com.quarantinefriends.dto.MessageDTO;
import com.quarantinefriends.entity.Message;
import com.quarantinefriends.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageDao {

    private MessageRepository messageRepository;

    @Autowired
    public MessageDao(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public static MessageDTO mapToDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        if(message!=null) {
            messageDTO.setId(message.getId());
            messageDTO.setMessage(message.getMessage());
            messageDTO.setFromUser(UserDao.mapToDTO(message.getFromUser()));
            messageDTO.setToUser(UserDao.mapToDTO(message.getToUser()));
        }
        return messageDTO;
    }

    public static Message mapToEntity(MessageDTO messageDTO) {
        Message message = new Message();
        if(messageDTO!=null) {
            message.setId(messageDTO.getId());
            message.setMessage(messageDTO.getMessage());
            message.setFromUser(UserDao.mapToEntity(messageDTO.getFromUser()));
            message.setToUser(UserDao.mapToEntity(messageDTO.getToUser()));
        }
        return message;
    }

    public List<MessageDTO> findMessagesForFriends(Long friendOneId, Long friendTwoId) {
        return this.messageRepository.findMessagesForFriends(friendOneId, friendTwoId).stream().map(MessageDao::mapToDTO).collect(Collectors.toList());
    }

    public void save(MessageDTO messageDTO) {
        this.messageRepository.save(mapToEntity(messageDTO));
    }

    public Long getNrExchangedMessages() {
        return this.messageRepository.count();
    }
}
