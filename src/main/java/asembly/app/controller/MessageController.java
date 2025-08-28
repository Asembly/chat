package asembly.app.controller;

import asembly.app.dto.SendMessageDto;
import asembly.app.entity.Message;
import asembly.app.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @MessageMapping("/messages")
    @SendTo("/topic/chat")
    public ResponseEntity<Message> send(SendMessageDto sendDto)
    {
        return messageService.send(sendDto);
    }

    @DeleteMapping
    public ResponseEntity<String> delete(String id)
    {
        return messageService.delete(id);
    }

}
