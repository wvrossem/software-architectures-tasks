package softarch.portal.db.flatfile;

import softarch.portal.data.RawData;
import softarch.portal.data.RegularData;
import softarch.portal.data.UserProfile;
import softarch.portal.db.DatabaseFacade;
import softarch.portal.db.sql.SQLDatabaseException;

import java.util.List;
import java.util.Date;
import java.util.Vector;

/**
 * This class implements a facade for all of the database layer's functionality.
 * @author Niels Joncheere
 */
public class FlatFileDatabaseFacade extends DatabaseFacade {
	private UserDatabase	userDb;

	/**
	 * Creates a new database facade.
	 */
	public FlatFileDatabaseFacade() {
		userDb		= new UserDatabase();
	}

	/**
	 * Inserts a new user profile into the user database.
	 */
	public void insert(UserProfile profile)
		throws FlatFileDatabaseException {
	
		userDb.insert(profile);
	}

	/**
	 * Updates an existing user profile in the user database.
	 */
	public void update(UserProfile profile)
		throws FlatFileDatabaseException {

		userDb.update(profile);
	}

	/**
	 * Returns the user with the specified username.
	 * @throws SQLDatabaseException 
	 */
	public UserProfile findUser(String username)
		throws FlatFileDatabaseException {

		return userDb.findUser(username);
	}

	/**
	 * Checks whether a user with the specified username exists.
	 */
	public boolean userExists(String username)
		throws FlatFileDatabaseException {

		return userDb.userExists(username);
	}

	/**
	 * Returns a list containing all records of the given information type
	 * that match the given query string.
	 */
	public List findRecords(String informationType, String queryString)
		throws FlatFileDatabaseException {

		//throw new FlatFileDatabaseException("Not implemented");
		return new Vector();
	}

	/**
	 * Returns a list containing all records of the given information type
	 * that were added after the given date.
	 */
	public List findRecordsFrom(String informationType, Date date)
		throws FlatFileDatabaseException {

		//throw new FlatFileDatabaseException("Not implemented");
		return new Vector();
	}

	/**
	 * Adds a new regular data object to the regular database.
	 */
	public void add(RegularData rd)
		throws FlatFileDatabaseException {
	
		//throw new FlatFileDatabaseException("Not implemented");
	}

	/**
	 * Returns the number of records of the given information type in the
	 * regular database.
	 */
	public int getNumberOfRegularRecords(String informationType)
		throws FlatFileDatabaseException {

		//throw new FlatFileDatabaseException("Not implemented");
		return 0;
	}

	/**
	 * Returns a list of all raw data.
	 */
	public List getRawData()
		throws FlatFileDatabaseException {

		//throw new FlatFileDatabaseException("Not implemented");
		return new Vector();
	}

	/**
	 * Returns a specific raw data object.
	 */
	public RawData getRawData(int id)
		throws FlatFileDatabaseException {

		throw new FlatFileDatabaseException("The method 'getRawData' is not implemented");
	}

	public void addRawData(RegularData rd)
		throws FlatFileDatabaseException {

		//throw new FlatFileDatabaseException("Not implemented");
	}

	/**
	 * Deletes a raw data object.
	 */
	public void deleteRawData(RawData rd)
		throws FlatFileDatabaseException {

		//throw new FlatFileDatabaseException("Not implemented");
	}

	/**
	 * Updates a raw data object.
	 */
	public void updateRawData(RawData rd)
		throws FlatFileDatabaseException {

		//throw new FlatFileDatabaseException("Not implemented");
	}

	/**
	 * Returns the number of records in the raw database.
	 */
	public int getNumberOfRawRecords()
		throws FlatFileDatabaseException {

		//throw new FlatFileDatabaseException("Not implemented");
		return 0;
	}
}
