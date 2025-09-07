package asembly.app.controller;

import asembly.app.dto.auth.SignInRequest;
import asembly.app.dto.auth.SignUpRequest;
import asembly.app.service.AuthService;
import asembly.app.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/refresh/{token}")
    public ResponseEntity<?> updateAccessToken(@PathVariable String token){
        return refreshTokenService.updateAccessToken(token);
    }

    @DeleteMapping("/logout/{token}")
    public ResponseEntity<String> logout(@PathVariable String token)
    {
        return refreshTokenService.logout(token);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest user)
    {
        return authService.signUp(user);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody SignInRequest userDto){
        return authService.signIn(userDto);
    }

}
