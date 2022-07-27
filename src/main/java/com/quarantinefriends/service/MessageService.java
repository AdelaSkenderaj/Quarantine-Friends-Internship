package com.quarantinefriends.service;

import com.quarantinefriends.dao.MessageDao;
import com.quarantinefriends.dto.MessageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private MessageDao messageDao;

    @Autowired
    public MessageService(MessageDao messageDao) {
        this.messageDao = messageDao;
    }
    public List<MessageDTO> getMessagesForFriends(Long friendOneId, Long friendTwoId) {
        return this.messageDao.findMessagesForFriends(friendOneId, friendTwoId);
    }

    public void save(MessageDTO messageDTO) {
        this.messageDao.save(messageDTO);
    }
}
