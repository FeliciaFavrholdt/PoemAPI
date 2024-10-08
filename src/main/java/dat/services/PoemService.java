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

	// Create a new poem and return PoemDTO
	public PoemDTO createPoem(Poem poem) {
		Poem createdPoem = poemDAO.create(poem);
		return new PoemDTO(createdPoem);
	}

	// Update an existing poem by ID
	public PoemDTO updatePoem(Long id, Poem updatedPoem) {
		// Fetch the existing poem by its ID
		Poem existingPoem = poemDAO.read(id);

		// If the poem is not found, return null
		if (existingPoem == null) {
			return null;
		}

		// Update the fields of the existing poem with the new data
		existingPoem.setTitle(updatedPoem.getTitle());
		existingPoem.setAuthor(updatedPoem.getAuthor());
		existingPoem.setPoem(updatedPoem.getPoem());
		existingPoem.setType(updatedPoem.getType());

		// Save the updated poem
		Poem updatedEntity = poemDAO.update(existingPoem);

		// Return the updated PoemDTO
		return new PoemDTO(updatedEntity);
	}

	public boolean deletePoemById(Long id) {
		// Call the poemDAO delete method and return the result (true if deleted, false otherwise)
		return poemDAO.delete(id);
	}
}
