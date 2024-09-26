package dat.controllers;

import dat.dtos.PoemDTO;
import dat.entities.Poem;
import dat.services.PoemService;
import io.javalin.http.Context;

import java.util.List;

public class PoemController {

	private final PoemService poemService;

	//	Constructor
	public PoemController (PoemService poemService) {
		this.poemService = poemService;
	}

	//	GET	/poems	Returns all poems from the database
	public void getAllPoems (Context context) {
		List<PoemDTO> poems = poemService.getAllPoems();

		if (poems != null) {
			context.status(200);
			context.json(poems);
		} else {
			context.status(404).result("No poems found");
		}
	}

	//	GET	/poem/{id}	Returns a specific poem based on the id
	public void getPoemById (Context context) {
		Long id = Long.parseLong(context.pathParam("id"));
		PoemDTO poem = poemService.getPoemById(id);

		if (poem != null) {
			context.json(poem);
		} else {
			context.status(404).result("Poem not found");

		}
	}

	// POST /poem - Create a new poem
	public void createPoem (Context context) {
		Poem newPoem = context.bodyAsClass(Poem.class);
		PoemDTO poem = poemService.createPoem(newPoem);
		context.status(201).json(poem);
	}

	// PUT /poem/{id} - Update an existing poem
	public void updatePoem (Context context) {
		Long id = Long.parseLong(context.pathParam("id"));
		Poem updatedPoem = context.bodyAsClass(Poem.class);
		PoemDTO poem = poemService.updatePoem(id, updatedPoem);
		if (poem != null) {
			context.status(200).json(poem);
		} else {
			context.status(404).result("Poem not found");
		}
	}


	// DELETE /poem/{id} - Delete a poem
	public void deletePoem (Context context) {
		Long id = Long.parseLong(context.pathParam("id"));
		if (poemService.deletePoemById(id)) {
			context.status(204);
		} else {
			context.status(404).result("Poem not found");
		}
	}
}