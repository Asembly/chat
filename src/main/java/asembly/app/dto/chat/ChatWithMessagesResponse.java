package asembly.app.dto.chat;

import asembly.app.dto.message.MessageResponse;

import java.util.List;

public record ChatWithMessagesResponse(String id, String title, List<MessageResponse> messages){}
