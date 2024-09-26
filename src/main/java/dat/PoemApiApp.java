package dat;

import dat.controllers.PoemController;
import dat.daos.PoemDAO;
import dat.dtos.PoemDTO;
import dat.entities.Poem;
import dat.services.PoemService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class PoemApiApp {
	static PoemDAO poemDAO = new PoemDAO();
	static PoemService poemService = new PoemService(poemDAO);
	static PoemController poemController = new PoemController(poemService);

	public static void main(String[] args) {
		// Set up the database
		PoemDatabasePopulator.populateDatabaseFromFile("src/main/resources/poems.txt");

		// Initialize Javalin
		Javalin app = Javalin.create(config -> {
			config.showJavalinBanner = false;
			config.bundledPlugins.enableRouteOverview("/routes");
			config.router.contextPath = "/api"; // Set the base path for all routes
		});

		// Set up routes with named methods as handlers
		app.get("/", PoemApiApp::handleRoot);
		app.get("/poem", PoemApiApp::handleGetAllPoems);
		app.get("/poem/{id}", PoemApiApp::handleGetPoemById);
		app.post("/poem", PoemApiApp::handleCreatePoem);
		app.put("/poem/{id}", PoemApiApp::handleUpdatePoem);
		app.delete("/poem/{id}", PoemApiApp::handleDeletePoem);

		// Exception handling example
		app.exception(Exception.class, (e, context) -> {
			context.status(500).result("Internal Server Error");
		});

		// Start the server
		app.start(7007);
	}

	// app.get("/poem", poemController::getAllPoems);
	// app.get("/poem/{id}", poemController::getPoemById);
	// app.post("/poem", poemController::createPoem);
	// app.put("/poem/{id}", poemController::updatePoem);
	// app.delete("/poem/{id}", poemController::deletePoem);

	// Named handler method for the root route (GET /)
	public static void handleRoot(Context context) {
		context.result("Hello! This is our Poem API");
	}

	// Named handler method for the GET /poem route
	public static void handleGetAllPoems(Context context) {
		poemController.getAllPoems(context);
	}

	// Named handler method for the GET /poem/{id} route
	public static void handleGetPoemById(Context context) {
		String id = context.pathParam("id");
		context.result("Fetching poem with ID: " + id);
		poemController.getPoemById(context);
	}

	// Named handler method for the POST /poem route
	public static void handleCreatePoem(Context context) {
		context.result("Creating a new poem");
		poemController.createPoem(context);
	}

	// Named handler method for the PUT /poem/{id} route
	public static void handleUpdatePoem(Context context) {
		String id = context.pathParam("id");
		context.result("Updating poem with ID: " + id);
		poemController.updatePoem(context);
	}

	// Named handler method for the DELETE /poem/{id} route
	public static void handleDeletePoem(Context context) {
		String id = context.pathParam("id");
		context.result("Deleting poem with ID: " + id);
		poemController.deletePoem(context);
	}
}