package asembly.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@Table(name = "refresh_tokens")
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    private String id;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(unique = true, nullable = false)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;
}
