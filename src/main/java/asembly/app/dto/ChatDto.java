package asembly.app.dto;

import java.util.List;

public record ChatDto(String id, String title, List<ChatUsersDto> users){}
