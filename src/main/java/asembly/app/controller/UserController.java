package asembly.app.controller;

import asembly.app.dto.*;
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
    public ResponseEntity<?> create(@RequestBody CreateUserDto userDto)
    {
        return userService.create(userDto);
    }

    @PostMapping("/{id}/chat")
    public ResponseEntity<ChatDto> createChat(@PathVariable String id, @RequestBody CreateChatDto chatDto)
    {
        return userService.createChat(id, chatDto);
    }

    @PatchMapping("/{id}/chat")
    public ResponseEntity<?> addChat(@PathVariable String id, @RequestBody AddChatDto chatDto)
    {
        return userService.addChat(id, chatDto);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll()
    {
        return userService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> findById(@PathVariable String id)
    {
        return userService.findById(id);
    }

}
