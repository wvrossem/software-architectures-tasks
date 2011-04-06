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
	protected String dbName;
	protected CsvController csvController;

	/**
	 * Creates a new database.
	 */
	public Database(String dbName) {
		this.dbName = dbName;
		
		csvController = new CsvController();
	}
	
	public void insert(CsvValues csvValues) {
		csvController.insert(dbName, csvValues);
	}
	
	public void update(CsvValues csvValues) {
		csvController.update(dbName, csvValues);
	}
	
	public Object find(Object el) throws DatabaseException {
		throw new DatabaseException("Not implemented");
	}
	
	public boolean exists(Object el) throws DatabaseException {
		throw new DatabaseException("Not implemented");
	}
}
