package softarch.portal.db.flatfile;

import softarch.portal.data.RawData;
import softarch.portal.data.RegularData;
import softarch.portal.data.UserProfile;

import java.util.List;
import java.util.Date;

/**
 * This class implements a facade for all of the database layer's functionality.
 * @author Niels Joncheere
 */
public class DatabaseFacade {
	private UserDatabase	userDb;

	/**
	 * Creates a new database facade.
	 */
	public DatabaseFacade(String dbUser, String dbPassword, String dbUrl) {
		userDb		= new UserDatabase(	dbUser,
							dbPassword,
							dbUrl);
	}

	/**
	 * Inserts a new user profile into the user database.
	 */
	public void insert(UserProfile profile)
		throws DatabaseException {
	
		userDb.insert(profile);
	}

	/**
	 * Updates an existing user profile in the user database.
	 */
	public void update(UserProfile profile)
		throws DatabaseException {

		userDb.update(profile);
	}

	/**
	 * Returns the user with the specified username.
	 */
	public UserProfile findUser(String username)
		throws DatabaseException {

		return userDb.findUser(username);
	}

	/**
	 * Checks whether a user with the specified username exists.
	 */
	public boolean userExists(String username)
		throws DatabaseException {

		return userDb.userExists(username);
	}

	/**
	 * Returns a list containing all records of the given information type
	 * that match the given query string.
	 */
	public List findRecords(String informationType, String queryString)
		throws DatabaseException {

		throw new DatabaseException("Not implemented");
	}

	/**
	 * Returns a list containing all records of the given information type
	 * that were added after the given date.
	 */
	public List findRecordsFrom(String informationType, Date date)
		throws DatabaseException {

		throw new DatabaseException("Not implemented");
	}

	/**
	 * Adds a new regular data object to the regular database.
	 */
	public void add(RegularData rd)
		throws DatabaseException {
	
		throw new DatabaseException("Not implemented");
	}

	/**
	 * Returns the number of records of the given information type in the
	 * regular database.
	 */
	public int getNumberOfRegularRecords(String informationType)
		throws DatabaseException {

		throw new DatabaseException("Not implemented");
	}

	/**
	 * Returns a list of all raw data.
	 */
	public List getRawData()
		throws DatabaseException {

		throw new DatabaseException("Not implemented");
	}

	/**
	 * Returns a specific raw data object.
	 */
	public RawData getRawData(int id)
		throws DatabaseException {

		throw new DatabaseException("Not implemented");
	}

	public void addRawData(RegularData rd)
		throws DatabaseException {

		throw new DatabaseException("Not implemented");
	}

	/**
	 * Deletes a raw data object.
	 */
	public void deleteRawData(RawData rd)
		throws DatabaseException {

		throw new DatabaseException("Not implemented");
	}

	/**
	 * Updates a raw data object.
	 */
	public void updateRawData(RawData rd)
		throws DatabaseException {

		throw new DatabaseException("Not implemented");
	}

	/**
	 * Returns the number of records in the raw database.
	 */
	public int getNumberOfRawRecords()
		throws DatabaseException {

		throw new DatabaseException("Not implemented");
	}
}
