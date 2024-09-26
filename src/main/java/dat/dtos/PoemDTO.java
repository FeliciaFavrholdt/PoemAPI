package dat.dtos;

import dat.entities.Poem;
import dat.enums.Type;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class PoemDTO {

	private Long id;
	private String title;
	private String author;
	private String poem;
	private Type type;

	public PoemDTO(Poem poem) {
		this.id = poem.getId();
		this.title = poem.getTitle();
		this.author = poem.getAuthor();
		this.poem = poem.getPoem();
		this.type = poem.getType();
	}
}