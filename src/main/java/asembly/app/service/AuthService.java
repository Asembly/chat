package asembly.app.service;

import asembly.app.dto.auth.SignInRequest;
import asembly.app.dto.auth.SignInResponse;
import asembly.app.dto.auth.SignUpRequest;
import asembly.app.dto.auth.access.AccessResponse;
import asembly.app.dto.auth.refresh.RefreshResponse;
import asembly.app.dto.user.UserCreateRequest;
import asembly.app.entity.User;
import asembly.app.error.ErrorMessage;
import asembly.app.mapping.TokenMapper;
import asembly.app.mapping.UserMapper;
import asembly.app.repository.RefreshTokenRepository;
import asembly.app.repository.UserRepository;
import asembly.app.security.JwtService;
import asembly.app.util.GeneratorId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TokenMapper tokenMapper;

    public ResponseEntity<?> signUp(SignUpRequest userDto)
    {
        if(userRepository.findByUsername(userDto.username()).isPresent())
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorMessage(
                            LocalDate.now(),
                            "user with username: " + userDto.username() + " found.",
                            HttpStatus.NOT_FOUND));

        User user = new User(
                GeneratorId.generateShortUuid(),
                userDto.username(),
                encoder.encode(userDto.password()),
                LocalDate.now(),
                null
        );

        log.info("USER SIGN UP " + user.getUsername());

        return ResponseEntity.ok(userService.create(new UserCreateRequest(
                user.getUsername(),
                user.getPassword())).getBody());
    }

    public ResponseEntity<?> signIn(SignInRequest userDto){
        var optionalUser = userRepository.findByUsername(userDto.username());

        if(optionalUser.isEmpty())
            return ResponseEntity.badRequest().body(new ErrorMessage(LocalDate.now(), "Object not found", HttpStatus.NOT_FOUND));

        var newUser = optionalUser.get();

        var optionalRefresh = refreshTokenRepository.findTokenByUserId(newUser.getId());
        log.info(optionalRefresh.toString());

        RefreshResponse refresh;

        if(optionalRefresh.isEmpty())
            refresh = refreshTokenService.generateRefreshToken(newUser.getId()).getBody();
        else
            refresh = tokenMapper.toTokenResponse(optionalRefresh.get());


        var access = jwtService.genJwt(userDto.username());

        if (encoder.matches(userDto.password(), newUser.getPassword()))
        {
            return ResponseEntity.ok(new SignInResponse(
                    newUser.getId(),
                    newUser.getUsername(),
                    new AccessResponse(access, jwtService.getExpiresAt(access).getTime()),
                    refresh
            ));
        }

        return ResponseEntity.badRequest().body("jwt not gen.");
    }
}
