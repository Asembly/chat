package asembly.app.service;

import asembly.app.entity.Chat;
import asembly.app.repository.ChatRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public ResponseEntity<List<Chat>> findAll()
    {
        return ResponseEntity.ok(chatRepository.findAll());
    }
}
