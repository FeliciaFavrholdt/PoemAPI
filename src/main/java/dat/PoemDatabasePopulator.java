package dat;

import dat.daos.PoemDAO;
import dat.entities.Poem;
import dat.enums.Type;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PoemDatabasePopulator {

	// Method to populate the database from a file
	public static void populateDatabaseFromFile(String filePath) {
		PoemDAO poemDAO = new PoemDAO();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			String title = null;
			String author = null;
			StringBuilder poemContent = new StringBuilder();

			while ((line = reader.readLine()) != null) {
				// Handle title
				if (line.startsWith("Title: ")) {
					title = line.substring(7).trim();
				}
				// Handle author
				else if (line.startsWith("Author: ")) {
					author = line.substring(8).trim();
				}
				// Handle poem content
				else if (line.startsWith("Poem:")) {
					poemContent = new StringBuilder(); // Start reading the poem
				}
				// Read poem content
				else if (!line.equals("---")) {
					poemContent.append(line).append("\n"); // Add poem lines
				}
				// When the delimiter `---` is reached, save the poem
				else if (line.equals("---")) {
					// If we have a valid title, author, and poem content, save the poem
					if (title != null && author != null && poemContent.length() > 0) {
						Poem poem = new Poem(title, author, poemContent.toString().trim(), Type.VILLANELLE);
						poemDAO.create(poem);
					}
					// Reset the title, author, and content for the next poem
					title = null;
					author = null;
					poemContent.setLength(0);
				}
			}

			// After the loop ends, insert the last poem (if it hasn't been inserted yet)
			if (title != null && author != null && poemContent.length() > 0) {
				Poem poem = new Poem(title, author, poemContent.toString().trim(), Type.VILLANELLE);
				poemDAO.create(poem);
			}

			System.out.println("Database populated with poems from file!");

		} catch (IOException e) {
			throw new RuntimeException("Error reading file: " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		// Specify the path to your poems file
		String filePath = "src/main/resources/poems.txt";
		populateDatabaseFromFile(filePath);
	}
}