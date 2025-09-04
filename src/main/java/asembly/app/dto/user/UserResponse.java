package asembly.app.dto.user;

import java.time.LocalDate;

public record UserResponse(String id, String username, LocalDate created_at) {}
