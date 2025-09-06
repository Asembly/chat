package asembly.app.dto.auth.refresh;

import java.time.Instant;

public record RefreshResponse(String token, Instant expiryDate){}
