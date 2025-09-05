package asembly.app.service;

import asembly.app.dto.chat.ChatCreateRequest;
import asembly.app.dto.user.UserCreateRequest;
import asembly.app.dto.user.UserResponse;
import asembly.app.dto.user.UserWithChatsResponse;
import asembly.app.entity.Chat;
import asembly.app.entity.User;
import asembly.app.error.ErrorMessage;
import asembly.app.mapping.ChatMapper;
import asembly.app.mapping.UserMapper;
import asembly.app.repository.ChatRepository;
import asembly.app.repository.UserRepository;
import asembly.app.util.GeneratorId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ChatMapper chatMapper;

    public ResponseEntity<?> addChat(String user_id, String chat_id)
    {
        var optionalUser = userRepository.findById(user_id);
        var optionalChat = chatRepository.findById(chat_id);

        if(optionalChat.isEmpty() || optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body(new ErrorMessage(LocalDate.now(), "Object not found", HttpStatus.NOT_FOUND));
        }

        var user = optionalUser.get();
        var chat = optionalChat.get();

        for(Chat item: user.getChats())
        {
            if(item.getId().equals(chat_id))
                return ResponseEntity.badRequest().build();
        }

        user.addChat(chat);

        userRepository.save(user);

        log.info("Chat add to user with id: {}.", user.getId());

        return ResponseEntity.ok(chatMapper.toChatResponse(chat));
    }

    public ResponseEntity<List<UserResponse>> findAll()
    {
        var users = userRepository.findAll();
        return ResponseEntity.ok(userMapper.toUserResponseList(users));
    }

    public ResponseEntity<UserWithChatsResponse> findById(String id)
    {
        var user = userRepository.findById(id);

        return user.map(value -> ResponseEntity.ok(userMapper.toUserWithChats(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    public ResponseEntity<UserResponse> create(UserCreateRequest dto)
    {
        if(userRepository.findByUsername(dto.username()).isPresent())
            return ResponseEntity.badRequest().build();

        var newUser = new User(
                GeneratorId.generateShortUuid(),
                dto.username(),
                dto.password(),
                LocalDate.now(),
                null);

        userRepository.save(newUser);

        log.info("User is created.");

        return ResponseEntity.ok(userMapper.toUserResponse(newUser));
    }

    public ResponseEntity<?> createChat(String id, ChatCreateRequest dto)
    {
        if(dto == null || dto.title() == null)
            return ResponseEntity.badRequest().body(
                    new ErrorMessage(LocalDate.now(),"Request body not valid", HttpStatus.BAD_REQUEST));

        var user = userRepository.findById(id).orElseThrow();

        var chat = new Chat(
                GeneratorId.generateShortUuid(),
                dto.title(),
                LocalDate.now(),
                List.of(),
                List.of()
        );

        var savedChat = chatRepository.save(chat);
        savedChat.addUser(user);

        if(dto.usernames() != null)
        {
            for(String item : dto.usernames())
            {
                if(!item.equals(user.getUsername()))
                {
                   var groupUser = userRepository.findByUsername(item).orElseThrow();
                   savedChat.addUser(groupUser);
                }
            }
        }

        log.info("Chat is created.");
        chatRepository.save(savedChat);

        return ResponseEntity.ok(chatMapper.toChatResponse(savedChat));
    }

}
