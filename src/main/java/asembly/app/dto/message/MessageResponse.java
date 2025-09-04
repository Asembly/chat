package asembly.app.dto.message;

import java.time.LocalDate;

public record MessageResponse(String id, String text, String author_id, LocalDate created_at) {}

