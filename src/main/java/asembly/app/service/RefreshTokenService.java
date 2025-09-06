package asembly.app.service;

import asembly.app.dto.auth.access.AccessResponse;
import asembly.app.dto.auth.refresh.RefreshResponse;
import asembly.app.entity.RefreshToken;
import asembly.app.entity.User;
import asembly.app.error.ErrorMessage;
import asembly.app.mapping.TokenMapper;
import asembly.app.repository.RefreshTokenRepository;
import asembly.app.repository.UserRepository;
import asembly.app.security.JwtService;
import asembly.app.util.GeneratorId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenService {

    @Value("${spring.jwt.refresh.expiration}")
    private Long refreshTokenExpiration;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private TokenMapper tokenMapper;

    public ResponseEntity<String> logout(String refresh_token)
    {
        var response = refreshTokenRepository.findByToken(refresh_token).orElseThrow();
        refreshTokenRepository.delete(response);
        return ResponseEntity.ok("User logout.");
    }

    public ResponseEntity<RefreshResponse> generateRefreshToken(String userId)
    {
        User user = userRepository.findById(userId).orElseThrow();
        var token = new RefreshToken(
                    GeneratorId.generateShortUuid(),
                    user,
                    UUID.randomUUID().toString(),
                    Timestamp.from(Instant.now().plusMillis(refreshTokenExpiration)).getTime()
                );

        refreshTokenRepository.save(token);

        return ResponseEntity.ok(tokenMapper.toTokenResponse(token));
    }

    public ResponseEntity<?> updateAccessToken(String refresh_token){
        var token = refreshTokenRepository.findByToken(refresh_token).orElseThrow();

        if(isTokenExpired(token))
        {
            refreshTokenRepository.delete(token);
            return ResponseEntity.badRequest().body(new ErrorMessage(
                    LocalDate.now(),
                    "Token was expired. login again",
                    HttpStatus.BAD_REQUEST));
        }

        String newJwt = jwtService.genJwt(token.getUser().getUsername());

        if(newJwt == null)
            return ResponseEntity.badRequest().body(new ErrorMessage(
                    LocalDate.now(),
                    "Token not generated",
                    HttpStatus.BAD_REQUEST));

        return ResponseEntity.ok(new AccessResponse(
                newJwt,
                jwtService.getExpiresAt(newJwt).getTime()));
    }

    public boolean isTokenExpired(RefreshToken token) {
        return Time.from(Instant.now()).getTime() > token.getExpires_at();
    }
}
