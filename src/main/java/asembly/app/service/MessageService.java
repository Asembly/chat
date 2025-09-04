package asembly.app.service;

import asembly.app.dto.message.MessageResponse;
import asembly.app.mapping.MessageMapper;
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
    @Autowired
    private MessageMapper messageMapper;

    public ResponseEntity<List<MessageResponse>> findAll()
    {
        var messages = messageRepository.findAll();

        log.info("Message displayed.");

        return ResponseEntity.ok(messageMapper.toMessageResponseList(messages));
    }

    public ResponseEntity<MessageResponse> findById(String id)
    {
       var message = messageRepository.findById(id);

       return message.map(value -> ResponseEntity.ok(messageMapper.toMessageResponse(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }
}
