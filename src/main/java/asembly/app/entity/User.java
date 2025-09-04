package asembly.app.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;
    @NotBlank
    @Size(min = 6)
    private String username;
    @NotBlank
    @Size(min = 8)
    private String password;
    @Temporal(TemporalType.DATE)
    private LocalDate created_at;

    @ManyToMany
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    @JoinTable(
            name = "user_chats",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    private List<Chat> chats = new LinkedList<>();

    public void addChat(Chat chat)
    {
        chats.add(chat);
        chat.getUsers().add(this);
    }

}
