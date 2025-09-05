package asembly.app.error;

import org.springframework.http.HttpStatusCode;

import java.time.LocalDate;

public record ErrorMessage(LocalDate caused_at, String message, HttpStatusCode status) {}
