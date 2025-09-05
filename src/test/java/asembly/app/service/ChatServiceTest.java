package asembly.app.service;

import asembly.app.dto.chat.ChatResponse;
import asembly.app.dto.chat.ChatWithMessagesResponse;
import asembly.app.dto.chat.ChatWithUsersResponse;
import asembly.app.dto.user.UserResponse;
import asembly.app.entity.Chat;
import asembly.app.entity.User;
import asembly.app.mapping.ChatMapper;
import asembly.app.mapping.MessageMapper;
import asembly.app.mapping.UserMapper;
import asembly.app.repository.ChatRepository;
import asembly.app.repository.MessageRepository;
import asembly.app.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ChatServiceTest.class);

    @Mock
    private ChatRepository chatRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private static ChatMapper chatMapper;

    @Mock
    private static UserMapper userMapper;

    @Mock
    private static MessageMapper messageMapper;

    @InjectMocks
    private ChatService chatService;


    @BeforeAll
    static void setup() {
        chatMapper = mock(ChatMapper.class);
    }

    @Test
    public void findAll_doReturnListChatResponse()
    {
        var chats = List.of(
                new Chat("1","chat1", LocalDate.now(),null, null),
                new Chat("2","chat2",LocalDate.now(),null,null)
        );

        var dto = List.of(
                new ChatResponse("1","chat1",LocalDate.now()),
                new ChatResponse("2","chat2",LocalDate.now()));

        when(chatRepository.findAll()).thenReturn(chats);
        when(chatMapper.toChatResponseList(chats)).thenReturn(dto);

        var result = chatService.findAll().getBody();

        assertEquals(dto, result);
        verify(chatRepository).findAll();
        verify(chatMapper).toChatResponseList(chats);
    }

    @Test
    public void findById_doReturnChatResponse()
    {
       var dto = new ChatWithUsersResponse("1", "chat1", null);
       var chat = new Chat("1", "chat1",LocalDate.now(), null, null);

       when(chatRepository.findById(chat.getId())).thenReturn(Optional.of(chat));
       when(chatMapper.toChatWithUsersResponse(chat)).thenReturn(dto);

       var result = chatService.findById(chat.getId()).getBody();

       assertEquals(dto, result);
       verify(chatRepository).findById(chat.getId());
       verify(chatMapper).toChatWithUsersResponse(chat);
    }

    @Test
    public void findByIdWithMessages_doReturnChatWithMessagesResponse()
    {
        var dto = new ChatWithMessagesResponse("1", "chat1", null);
        var chat = new Chat("1", "chat1",LocalDate.now(), null, null);

        when(chatRepository.findById(chat.getId())).thenReturn(Optional.of(chat));
        when(chatMapper.toChatWithMessagesResponse(chat)).thenReturn(dto);

        var result = chatService.findByIdWithMessages(chat.getId()).getBody();

        assertEquals(dto, result);
        verify(chatRepository).findById(chat.getId());
        verify(chatMapper).toChatWithMessagesResponse(chat);
    }

    @Test
    public void addUser_doReturnUserResponse()
    {
        var chat = new Chat("1", "chat1",LocalDate.now(), null, new LinkedList<>());
        var user = new User("1", "tester", "123456789",LocalDate.now(), new LinkedList<>());

        var userDto = new UserResponse(user.getId(),user.getUsername(),LocalDate.now());

        when(chatRepository.findById(chat.getId())).thenReturn(Optional.of(chat));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(chatRepository.save(chat)).thenReturn(chat);
        when(userMapper.toUserResponse(user)).thenReturn(userDto);

        var result = chatService.addUser(chat.getId(), user.getId()).getBody();

        assertEquals(userDto, result);
    }

//    @Test
//    public void createMessage_doReturnMessageResponse()
//    {
//        var chat = new Chat("123", "chat1", new LinkedList<>(), new LinkedList<>());
//        var user = new User("233", "tester", "123456789", new LinkedList<>());
//        var msg = new Message("423", "hello", user.getId(), chat);
//
//        var msgDto = new MessageResponse("423","hello", user.getId());
//
//        when(chatRepository.findById(chat.getId())).thenReturn(Optional.of(chat));
//        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//        when(messageRepository.save(msg)).thenReturn(msg);
//        when(messageMapper.toMessageResponse(msg)).thenReturn(msgDto);
//
//        var result = chatService.createMessage(chat.getId(), new MessageCreateRequest("hello", "233")).getBody();
//
//        assertEquals(msgDto, result);
//    }
}
