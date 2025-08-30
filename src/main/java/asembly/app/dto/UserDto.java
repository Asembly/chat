package asembly.app.dto;

import java.util.List;

public record UserDto(String id, String username, List<UserChatsDto> chats) {
}
