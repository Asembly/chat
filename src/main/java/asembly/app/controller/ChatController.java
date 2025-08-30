package asembly.app.controller;

import asembly.app.dto.AddUserDto;
import asembly.app.dto.ChatDto;
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
    public ResponseEntity<List<ChatDto>> findAll()
    {
       return chatService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatDto> findById(@PathVariable String id)
    {
        return chatService.findById(id);
    }

    @PatchMapping("/{id}/user")
    public ResponseEntity<?> addUser(String id, AddUserDto userDto)
    {
        return chatService.addUser(id, userDto);
    }
}
