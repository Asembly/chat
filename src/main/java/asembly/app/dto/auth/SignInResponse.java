package asembly.app.dto.auth;

import asembly.app.dto.auth.access.AccessResponse;
import asembly.app.dto.auth.refresh.RefreshResponse;

public record SignInResponse(
        String user_id,
        String username,
        AccessResponse access,
        RefreshResponse refresh
){}
