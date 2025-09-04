package asembly.app.service;

import asembly.app.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class SocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendMessageToChat(String chat_id, Message message) {
        String destination = "/topic/chat/" + chat_id;
        simpMessagingTemplate.convertAndSend(destination, message);
    }

}
