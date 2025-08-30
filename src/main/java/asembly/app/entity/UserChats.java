package asembly.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Table(name = "user_chats")
@AllArgsConstructor
@NoArgsConstructor
public class UserChats {

    @Column(name = "user_id")
    private String user_id;
    @Column(name = "chat_id")
    private String chat_id;

}
