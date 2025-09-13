package asembly.app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "chats")
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    @Id
    private String id;

    @NotBlank
    private String title;

    @Temporal(TemporalType.DATE)
    private LocalDate created_at;

}
