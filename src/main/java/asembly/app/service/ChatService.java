package asembly.app.service;

import asembly.app.dto.*;
import asembly.app.entity.Chat;
import asembly.app.entity.User;
import asembly.app.repository.ChatRepository;
import asembly.app.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> addUser(String chat_id, AddUserDto userDto)
    {
        var user = userRepository.findById(userDto.user_id()).orElseThrow();
        var chat = chatRepository.findById(chat_id).orElseThrow();

        for(User item: chat.getUsers())
        {
            if(item.getId().equals(userDto.user_id()))
            {
                return ResponseEntity.badRequest().body(
                        String.format(
                                "User with id: %s already exist for this chat.",
                                userDto.user_id()
                        ));
            }
        }

        chat.addUser(user);

        chatRepository.save(chat);

        log.info("User add to chat with id: {}.", chat.getId());

        return ResponseEntity.ok(new ChatUsersDto(user.getId(), user.getUsername()));
    }

    public ResponseEntity<List<ChatDto>> findAll()
    {
        List<ChatDto> chatDto = new LinkedList<>();
        var chats = chatRepository.findAll();

        for(Chat chat : chats)
        {
            chatDto.add(new ChatDto(
                    chat.getId(),
                    chat.getTitle(),
                    Objects.requireNonNull(
                            findById(chat.getId()).getBody()).users()
            ));
        }

        log.info("Chats displayed.");

        return ResponseEntity.ok(chatDto);
    }

    public ResponseEntity<ChatDto> findById(String id)
    {
        List<ChatUsersDto> chatUsersDto = new LinkedList<>();
        var chat = chatRepository.findById(id).orElseThrow();

        for(User user: chat.getUsers())
            chatUsersDto.add(new ChatUsersDto(user.getId(),user.getUsername()));

        log.info("Chat with id: {} displayed.", chat.getId());
        return ResponseEntity.ok(new ChatDto(chat.getId(),chat.getTitle(),chatUsersDto));
    }

}
