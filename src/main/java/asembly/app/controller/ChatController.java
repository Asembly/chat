package asembly.app.controller;

import asembly.app.dto.chat.ChatResponse;
import asembly.app.dto.chat.ChatWithMessagesResponse;
import asembly.app.dto.chat.ChatWithUsersResponse;
import asembly.app.dto.message.MessageCreateRequest;
import asembly.app.dto.message.MessageResponse;
import asembly.app.dto.user.UserResponse;
import asembly.app.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public ResponseEntity<List<ChatResponse>> findAll()
    {
       return chatService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatWithUsersResponse> findById(@PathVariable String id)
    {
        return chatService.findById(id);
    }

    @GetMapping("/{id}/message")
    public ResponseEntity<ChatWithMessagesResponse> findByIdWithMessages(@PathVariable String id)
    {
        return chatService.findByIdWithMessages(id);
    }

    @PatchMapping("/{chat_id}/user/{user_id}")
    public ResponseEntity<UserResponse> addUser(@PathVariable String chat_id, @PathVariable String user_id)
    {
        return chatService.addUser(chat_id, user_id);
    }


    @PostMapping("/{id}/message")
    public ResponseEntity<MessageResponse> createMessage(@PathVariable String id, @RequestBody MessageCreateRequest dto)
    {
       return chatService.createMessage(id, dto);
    }
}
