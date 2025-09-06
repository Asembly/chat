package asembly.app.repository;

import asembly.app.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    @Query(value = "select * from refresh_tokens where token = :token", nativeQuery = true)
    public Optional<RefreshToken> findByToken(String token);

    @Query(value = "select * from refresh_tokens where user_id = :user_id", nativeQuery = true)
    public Optional<RefreshToken> findTokenByUserId(String user_id);
}
