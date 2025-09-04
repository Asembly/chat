package asembly.app.controller;

import asembly.app.dto.message.MessageResponse;
import asembly.app.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    public ResponseEntity<List<MessageResponse>> findAll()
    {
        return messageService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> findById(String id)
    {
        return messageService.findById(id);
    }
}
