package com.quarantinefriends.controller;

import com.quarantinefriends.dto.MessageDTO;
import com.quarantinefriends.entity.Message;
import com.quarantinefriends.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")

public class MessageController {

    private MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/messages/{friendOneId}/{friendTwoId}")
    public List<MessageDTO> getMessagesForFriends(@PathVariable Long friendOneId, @PathVariable Long friendTwoId) {
        return this.messageService.getMessagesForFriends(friendOneId, friendTwoId);
    }

    @PostMapping("/messages")
    public void addMessage(@RequestBody MessageDTO messageDTO) {
        this.messageService.save(messageDTO);
    }
}
