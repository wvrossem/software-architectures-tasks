package be.library;

import java.util.Date;

/**
 * Example Service of Library.
 * @author Stefan Marr
 */
public class LibraryService {

	/**
	 * This queries for books in the local database.
	 * 
	 * @param query is currently ignored to return two dummy results 
	 * @return two dummy results
	 */
	public final Book[] searchForBooks(final String query) {
		Book[] results = new Book[2];
		
		Book r = new Book();
		r.setAuthor("William Sleator");
		r.setDate(new Date(2008, 3, 1));
		r.setIsbn("0810993562");
		r.setPublisher("Amulet Books");
		r.setTitle("Test");
		r.setLanguage("English");
		
		results[0] = r;
		
		r = new Book();
		r.setAuthor("Douglas Adams");
		r.setDate(new Date(2002, 4, 30));
		r.setIsbn("0345453743");
		r.setPublisher("Del Rey");
		r.setTitle("The Ultimate Hitchhiker's Guide to the Galaxy");
		r.setLanguage("English");
		
		results[1] = r;
		
		return results;
	}
}
