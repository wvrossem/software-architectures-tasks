package softarch.portal.app;

import softarch.portal.db.DatabaseException;
import softarch.portal.db.DatabaseFacade;
import softarch.portal.db.sql.SQLDatabaseException;
import softarch.portal.db.sql.SQLDatabaseFacade;

import java.util.List;
import java.util.Date;
import java.util.Vector;

import librarysearch.soft.Book;

/**
 * Handles all queries concerning the regular database.
 * @author Niels Joncheere
 */
public class QueryManager extends Manager {
	/**
	 * Creates a new query manager.
	 * @param dbFacade	The database facade the manager should use to
	 * 			access the database.
	 */
	public QueryManager(DatabaseFacade dbFacade, DatabaseFacade dbFacade2) {
		this.dbFacade = dbFacade;
		// Assignment 4 Modification.
		this.dbFacade2 = dbFacade2;
	}

	/**
	 * Returns a list containing all records of the given information type
	 * that match the given query string.
	 * @param informationType	The information type the user wants
	 * 				to query.
	 * @param queryString		The query string that should be used
	 * 				to carry out the search (for example
	 * 				"+foo -bar").
	 */
	public List findRecords(String informationType, String queryString)
		throws ApplicationException {

		try {
			// Assignment 4 Modification.
			List<Book> ret = new Vector<Book>();
			ret.addAll(dbFacade.findRecords(informationType, queryString));
			ret.addAll(dbFacade2.findRecords(informationType, queryString));
			return ret;
		}
		catch (DatabaseException e) { // MODIFIED by Wouter & Ken
			throw new ApplicationException(e.getMessage());
		}
		catch (Exception e) {
			throw new ApplicationException(
				"The query manager has caught an unexpected " +
				"exception: " + e.getMessage());
		}
	}

	/**
	 * Returns a list containing all records of the given information type
	 * that were added after the given date.
	 */
	public List findRecordsFrom(String informationType, Date date)
		throws ApplicationException {

		try {
			// Assignment 4 Modification.
			List<Book> ret = new Vector<Book>();
			ret.addAll(dbFacade.findRecordsFrom(informationType, date));
			ret.addAll(dbFacade2.findRecordsFrom(informationType, date));
			return ret;
		}
		catch (DatabaseException e) { // MODIFIED by Wouter & Ken
			throw new ApplicationException(e.getMessage());
		}
		catch (Exception e) {
			throw new ApplicationException(
				"The query manager has caught an unexpected " +
				"exception: " + e.getMessage());
		}
	}
}
