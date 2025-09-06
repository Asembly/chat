package asembly.app.dto.auth.refresh;

public record RefreshResponse(String token, Long expires_at){}
