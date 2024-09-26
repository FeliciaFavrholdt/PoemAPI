package dat.controllers;

import dat.daos.PoemDAO;
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
	public void createPoem(Context context) {
		try {
			// Parse the incoming JSON body into a Poem object
			Poem newPoem = context.bodyAsClass(Poem.class);

			// Convert the string type field to the Type enum using fromString
			newPoem.setType(Type.fromString(newPoem.getType().toString()));

			// Call service to create the poem and return as DTO
			PoemDTO poemDTO = poemService.createPoem(newPoem);

			// Respond with 201 Created and return the new PoemDTO
			context.status(201).json(poemDTO);
		} catch (IllegalArgumentException e) {
			context.status(400).result("Invalid poem type: " + e.getMessage());
		} catch (Exception e) {
			context.status(400).result("Error creating poem: " + e.getMessage());
		}
	}

	// PUT /poem/{id} - Update an existing poem
	public void updatePoem(Context context) {
		try {
			// Get the poem ID from the URL path
			Long id = Long.parseLong(context.pathParam("id"));

			// Parse the incoming JSON body as an updated Poem object
			Poem updatedPoem = context.bodyAsClass(Poem.class);

			// Convert the string type field to the Type enum using fromString
			updatedPoem.setType(Type.fromString(updatedPoem.getType().toString()));

			// Call the service to update the poem and return the updated PoemDTO
			PoemDTO updatedPoemDTO = poemService.updatePoem(id, updatedPoem);

			if (updatedPoemDTO != null) {
				context.json(updatedPoemDTO);
			} else {
				context.status(404).result("Poem not found");
			}
		} catch (IllegalArgumentException e) {
			context.status(400).result("Invalid poem type: " + e.getMessage());
		} catch (Exception e) {
			context.status(400).result("Error updating poem: " + e.getMessage());
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