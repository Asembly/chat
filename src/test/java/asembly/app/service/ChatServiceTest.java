package asembly.app.service;

import asembly.app.dto.chat.get.ChatDto;
import asembly.app.repository.ChatRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.LinkedList;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {

    @Mock
    private ChatRepository chatRepository;

    @InjectMocks
    private ChatService chatService;

    @Test
    public void findAll_doReturnListChats()
    {
        var chats = new LinkedList<ChatDto>();
        chats.add(new ChatDto("id1","title1"));
        chats.add(new ChatDto("id2","title2"));

        //given

        var test = chatRepository.findAllChats().orElseThrow();
        Mockito.doReturn(chats).when(test);

        //when

        var responseEntity = chatService.findAll();

        //then

        Assertions.assertNotNull(responseEntity);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Assertions.assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(chats, responseEntity.getBody());
    }

    @Test
    public void findById_doReturnChat()
    {

    }

    @Test
    public void addUser_doReturnChatUserDto()
    {

    }

}
