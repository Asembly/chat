package asembly.app.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "messages")
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    private String id;
    @NotBlank
    private String text;
    @NotBlank
    private String author_id;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    @JsonBackReference
    private Chat chat;
}
