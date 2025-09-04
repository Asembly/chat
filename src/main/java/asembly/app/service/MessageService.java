package asembly.app.service;

import asembly.app.entity.Message;
import asembly.app.repository.ChatRepository;
import asembly.app.repository.MessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MessageRepository messageRepository;

    public ResponseEntity<List<Message>> findAll()
    {
        var messages = messageRepository.findAll();

        log.info("Chats displayed.");

        return ResponseEntity.ok(messages);
    }

    public ResponseEntity<Message> findById(String id)
    {
       var message = messageRepository.findById(id).orElseThrow();

       log.info("Chat with id: {} displayed.", message.getId());

       return ResponseEntity.ok(message);
    }
}
