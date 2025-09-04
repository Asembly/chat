package asembly.app.dto.user;

import asembly.app.dto.chat.ChatResponse;

import java.util.List;

public record UserWithChatsResponse(String id, String username, List<ChatResponse> chats){}
