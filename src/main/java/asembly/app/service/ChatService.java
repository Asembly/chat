package asembly.app.service;

import asembly.app.entity.Chat;
import asembly.app.repository.ChatRepository;
import asembly.dto.chat.ChatCreateRequest;
import asembly.util.GeneratorId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public ResponseEntity<List<Chat>> findAll()
    {
        var chats = chatRepository.findAll();

        log.info("Chats displayed.");

        return ResponseEntity.ok(chats);
    }

    public ResponseEntity<Chat> findById(String id)
    {
       var chat = chatRepository.findById(id).orElseThrow();
       return ResponseEntity.ok(chat);
    }

    public ResponseEntity<Chat> create(ChatCreateRequest dto)
    {
        var chat = new Chat(
                GeneratorId.generateShortUuid(),
                dto.title(),
                LocalDate.now()
        );

        return ResponseEntity.ok(chatRepository.save(chat));
    }
}
