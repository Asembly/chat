package asembly.app.service;

import asembly.app.dto.SendMessageDto;
import asembly.app.entity.Message;
import asembly.app.repository.MessageRepository;
import asembly.app.util.GeneratorId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public ResponseEntity<List<Message>> findAll(){
        return ResponseEntity.ok(messageRepository.findAll());
    }

    public ResponseEntity<Message> findById(String id)
    {
        return ResponseEntity.ok(messageRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Message not found.")));
    }

    public ResponseEntity<Message> send(SendMessageDto sendDto)
    {
        var message = messageRepository.save(new Message(
                GeneratorId.generateShortUuid(),
                sendDto.sender_id(),
                sendDto.recipient_id(),
                sendDto.text()
        ));
        log.info("Message send " + sendDto.text());
        return ResponseEntity.ok(message);
    }

    public ResponseEntity<String> delete(String id)
    {
        var message = messageRepository.findById(id).orElseThrow();
        messageRepository.delete(message);
        return ResponseEntity.ok("Message delete.");
    }
}
