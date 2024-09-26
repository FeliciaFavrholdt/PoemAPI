package dat.services;

import dat.daos.PoemDAO;
import java.util.List;
import java.util.stream.Collectors;
import dat.dtos.PoemDTO;
import dat.entities.Poem;

public class PoemService {
	private final PoemDAO poemDAO = new PoemDAO();

	public PoemService (PoemDAO poemDAO) {
	}

	public List<PoemDTO> getAllPoems () {
		return poemDAO.getAll().stream()
				.map(PoemDTO::new)
				.collect(Collectors
						.toList());
	}

	public PoemDTO getPoemById(Long id) {
		Poem poem = poemDAO.read(id);

		// If the poem is found, convert it to PoemDTO; otherwise, return null
		if (poem != null) {
			return new PoemDTO(poem);
		} else {
			return null;
		}
	}

	public PoemDTO createPoem (Poem poem) {
		// Call the poemDAO create method and return the PoemDTO
		return new PoemDTO(poemDAO.create(poem));
	}

	public PoemDTO updatePoem(Long id, Poem updatedPoem) {
		// Fetch the existing poem by its ID
		Poem poem = poemDAO.read(id);

		// If the poem is not found, return null
		if (poem == null) {
			return null;
		}

		// Update the existing poem's fields with the new data
		poem.setTitle(updatedPoem.getTitle());
		poem.setAuthor(updatedPoem.getAuthor());
		poem.setPoem(updatedPoem.getPoem());
		poem.setType(updatedPoem.getType());

		// Save the updated poem
		Poem updatedEntity = poemDAO.update(poem);

		// Return the updated PoemDTO
		return new PoemDTO(updatedEntity);
	}

	public boolean deletePoemById(Long id) {
		// Call the poemDAO delete method and return the result (true if deleted, false otherwise)
		return poemDAO.delete(id);
	}
}
