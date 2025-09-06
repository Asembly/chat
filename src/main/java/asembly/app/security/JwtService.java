package asembly.app.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
public class JwtService {

    @Value("${spring.jwt.secret}")
    private String secretKey;
    @Value("${spring.jwt.access.expiration}")
    private int expirationMs;

    public String genJwt(String username){
        try{
            Algorithm alg = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withSubject(username)
                    .withIssuer("auth0")
                    .withIssuedAt(Timestamp.from(Instant.now()))
                    .withExpiresAt(Timestamp.from(Instant.now().plusMillis(expirationMs)))
                    .sign(alg);
        }catch(JWTCreationException e)
        {
            throw new JWTCreationException(e.getMessage() + "\nInvalid Signing configuration / Couldn't convert Claims.", e.getCause());
        }
    }

    public boolean verifyJwt(String token)
    {
        DecodedJWT decodedJWT;
        try{
            Algorithm alg = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(alg)
                    .withIssuer("auth0")
                    .build();
            decodedJWT = verifier.verify(token);
            return true;
        }catch (JWTVerificationException e) {
            throw new JWTVerificationException(e.getMessage() + "\nInvalid signature/claims", e.getCause());
        }
    }

    public Timestamp getIssuedAt(String token)
    {
        try {
            Algorithm alg = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(alg)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return Timestamp.from(decodedJWT.getIssuedAt().toInstant());
        }catch(JWTVerificationException exception)
        {
            return null;
        }
    }

    public Timestamp getExpiresAt(String token)
    {
        try {
            Algorithm alg = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(alg)
                    .withIssuer("auth0")
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);
            return Timestamp.from(decodedJWT.getExpiresAt().toInstant());
        }catch(JWTVerificationException exception)
        {
            return null;
        }
    }

    public String getUsernameFromJwt(String token)
    {
        DecodedJWT decodedJWT;
        try{
            Algorithm alg = Algorithm.HMAC256(secretKey);
            decodedJWT = JWT.require(alg)
                    .build()
                    .verify(token);
            return decodedJWT.getSubject();
        }catch (JWTVerificationException e) {
            throw new JWTVerificationException(e.getMessage() + "\nInvalid signature/claims", e.getCause());
        }
    }

}
