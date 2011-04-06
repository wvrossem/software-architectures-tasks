package softarch.portal.db.flatfile;

import softarch.portal.db.DatabaseException;

/**
 * @author Niels Joncheere
 */
public class FlatFileDatabaseException extends DatabaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new database exception.
	 */
	public FlatFileDatabaseException(String message) {
		super(message);
	}
}
