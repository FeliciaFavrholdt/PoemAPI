package dat.entities;

import dat.enums.Type;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "poems")
public class Poem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "poem", nullable = false, length = 1000, columnDefinition = "TEXT")
    private String poem;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;

    public Poem (String title, String author, String trim, Type type) {
        this.title = title;
        this.author = author;
        this.poem = trim;
        this.type = type;
    }
}