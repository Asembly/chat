package asembly.app.controller;

import asembly.app.dto.chat.ChatCreateRequest;
import asembly.app.dto.chat.ChatResponse;
import asembly.app.dto.user.UserCreateRequest;
import asembly.app.dto.user.UserResponse;
import asembly.app.dto.user.UserWithChatsResponse;
import asembly.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody UserCreateRequest dto)
    {
        return userService.create(dto);
    }

    @PostMapping("/{id}/chat")
    public ResponseEntity<ChatResponse> createChat(@PathVariable String id, @RequestBody ChatCreateRequest dto)
    {
        return userService.createChat(id, dto);
    }

    @PatchMapping("/{user_id}/chat/{chat_id}")
    public ResponseEntity<?> addChat(@PathVariable String user_id, @PathVariable String chat_id)
    {
        return userService.addChat(user_id, chat_id);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll()
    {
        return userService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<UserWithChatsResponse> findById(@PathVariable String id)
    {
        return userService.findById(id);
    }

}
