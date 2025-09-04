package asembly.app.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

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

    @JsonManagedReference
    @OneToMany(mappedBy = "chat")
    private List<Message> messages;

    @ManyToMany(mappedBy = "chats")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<User> users = new LinkedList<>();

    public void addUser(User user)
    {
        users.add(user);
        user.getChats().add(this);
    }

    public void addMessage(Message msg)
    {
        messages.add(msg);
        msg.setChat(this);
    }

}
