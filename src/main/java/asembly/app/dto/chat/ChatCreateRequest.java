package asembly.app.dto.chat;

import java.util.List;

public record ChatCreateRequest(String title, List<String> usernames) {}
