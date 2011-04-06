package softarch.portal.db.sql;

import softarch.portal.db.DatabaseException;

/**
 * @author Niels Joncheere
 */
public class SQLDatabaseException extends DatabaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new database exception.
	 */
	public SQLDatabaseException(String message) {
		super(message);
	}
}
