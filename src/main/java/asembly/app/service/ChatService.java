package asembly.app.service;

import asembly.app.dto.chat.ChatResponse;
import asembly.app.dto.chat.ChatWithUsersResponse;
import asembly.app.dto.message.MessageCreateRequest;
import asembly.app.dto.message.MessageResponse;
import asembly.app.dto.user.UserResponse;
import asembly.app.entity.Message;
import asembly.app.entity.User;
import asembly.app.mapping.ChatMapper;
import asembly.app.mapping.MessageMapper;
import asembly.app.mapping.UserMapper;
import asembly.app.repository.ChatRepository;
import asembly.app.repository.UserRepository;
import asembly.app.util.GeneratorId;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SocketService socketService;
    @Autowired
    private ChatMapper chatMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MessageMapper messageMapper;

    public ResponseEntity<UserResponse> addUser(String chat_id, String user_id)
    {
        var chat = chatRepository.findById(chat_id).orElseThrow();
        var user = userRepository.findById(user_id).orElseThrow();

        for(User item: chat.getUsers())
        {
            if(item.getId().equals(user_id))
                return ResponseEntity.badRequest().build();
        }

        chat.addUser(user);

        chatRepository.save(chat);

        log.info("User add to chat with id: {}.", chat.getId());

        return ResponseEntity.ok(userMapper.toUserResponse(user));
    }

    public ResponseEntity<MessageResponse> createMessage(String chat_id, MessageCreateRequest dto)
    {
        var chat = chatRepository.findById(chat_id).orElseThrow();
        var user = userRepository.findById(dto.author_id()).orElseThrow();

        if(!user.getChats().contains(chat))
            return ResponseEntity.notFound().build();

        var message = new Message(
                GeneratorId.generateShortUuid(),
                dto.text(),
                dto.author_id(),
                chat
        );

        chatRepository.save(chat);

        chat.addMessage(message);

        socketService.sendMessageToChat(chat_id, message);

        return ResponseEntity.ok(messageMapper.toMessageResponse(message));
    }

    public ResponseEntity<List<ChatResponse>> findAll()
    {
        var chats = chatRepository.findAll();

        log.info("Chats displayed.");

        return ResponseEntity.ok(chatMapper.toChatResponseList(chats));
    }

    public ResponseEntity<ChatWithUsersResponse> findById(String id)
    {
       var chat = chatRepository.findById(id);
       return chat.map(value -> ResponseEntity.ok(chatMapper.toChatWithUsersResponse(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
