package softarch.portal.db.flatfile;

import softarch.portal.db.DatabaseException;

/**
 * @author Niels Joncheere
 */
public class FlatFileDatabaseException extends Exception implements DatabaseException {
	/**
	 * Creates a new database exception.
	 */
	public FlatFileDatabaseException(String message) {
		super(message);
	}
}
