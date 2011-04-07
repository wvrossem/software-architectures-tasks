
// ADDED by Wouter & Ken

package softarch.portal.db.flatfile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Iterator;

import softarch.portal.data.UserProfile;

/**
 * This abstract class implements the behaviour that is to be shared
 * by all databases.
 * @author Niels Joncheere
 */
public class Database {
	protected CsvController csvController;

	/**
	 * Creates a new database.
	 */
	public Database() {
		csvController = new CsvController();
	}
	
	public void insert(CsvValues csvValues) throws FlatFileDatabaseException {
		throw new FlatFileDatabaseException("Not implemented");
	}
	
	public void update(CsvValues csvValues) throws FlatFileDatabaseException {
		throw new FlatFileDatabaseException("Not implemented");
	}
	
	public Object find(Object el) throws FlatFileDatabaseException {
		throw new FlatFileDatabaseException("Not implemented");
	}
	
	public boolean exists(Object el) throws FlatFileDatabaseException {
		throw new FlatFileDatabaseException("Not implemented");
	}
}
