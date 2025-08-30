package asembly.app.dto;

import java.util.List;

public record CreateChatDto(String title, List<String> users){}
