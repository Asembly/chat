package asembly.app.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "contents")
@AllArgsConstructor
@NoArgsConstructor
public class Content {
    @Id
    private String id;
    private String text;
}
