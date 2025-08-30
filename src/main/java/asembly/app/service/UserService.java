package asembly.app.service;

import asembly.app.dto.*;
import asembly.app.entity.Chat;
import asembly.app.entity.User;
import asembly.app.repository.ChatRepository;
import asembly.app.repository.UserRepository;
import asembly.app.util.GeneratorId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;

//    public ResponseEntity<Chat> addChat(String user_id, String chat_id)
//    {
//    }

    public ResponseEntity<List<UserDto>> findAll()
    {
        List<UserDto> userDto = new LinkedList<>();
        var users = userRepository.findAll();

        for(User user : users)
        {
            userDto.add(new UserDto(
                    user.getId(),
                    user.getUsername(),
                    Objects.requireNonNull(
                            findById(user.getId()).getBody()).chats()
            ));
        }

        log.info("Users displayed.");

        return ResponseEntity.ok(userDto);
    }

    public ResponseEntity<UserDto> findById(String id)
    {
       List<UserChatsDto> userChatsDto = new LinkedList<>();
       var user = userRepository.findById(id).orElseThrow();

       for(Chat chat: user.getChats())
           userChatsDto.add(new UserChatsDto(chat.getId(),chat.getTitle()));

       log.info("User with id: " + user.getId() + " displayed.");
       return ResponseEntity.ok(new UserDto(user.getId(),user.getUsername(),userChatsDto));
    }

    public ResponseEntity<?> create(CreateUserDto userDto)
    {
        if(userRepository.findByUsername(userDto.username()).isPresent())
            return ResponseEntity.badRequest().body("User already exist.");
        var user = new User(
                GeneratorId.generateShortUuid(),
                userDto.username(),
                userDto.password(),
                null);

        userRepository.save(user);

        log.info("User is created.");

        return ResponseEntity.ok(user);
    }

    public ResponseEntity<ChatDto> createChat(String id, CreateChatDto chatDto)
    {
        List<ChatUsersDto> chatUsersDto = new LinkedList<>();
        var user = userRepository.findById(id).orElseThrow();

        var chat = new Chat(
                GeneratorId.generateShortUuid(),
                chatDto.title(),
                List.of()
        );
        var savedChat = chatRepository.save(chat);
        savedChat.addUser(user);

        chatUsersDto.add(new ChatUsersDto(user.getId(), user.getUsername()));

        if(chatDto.users() != null)
        {
            for(String item : chatDto.users())
            {
                if(!item.equals(user.getId()))
                {
                   User groupUser = userRepository.findById(item).orElseThrow();
                   savedChat.addUser(groupUser);
                   chatUsersDto.add(new ChatUsersDto(groupUser.getId(), groupUser.getUsername()));
//                   groupUser.addChat(savedChat);
                }
            }
        }

        log.info("Chat is created.");
        chatRepository.save(savedChat);

        return ResponseEntity.ok(new ChatDto(savedChat.getId(), savedChat.getTitle(), chatUsersDto));
    }

}
