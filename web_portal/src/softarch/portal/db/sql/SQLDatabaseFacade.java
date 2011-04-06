package softarch.portal.db.sql;

import softarch.portal.data.RawData;
import softarch.portal.data.RegularData;
import softarch.portal.data.UserProfile;
import softarch.portal.db.DatabaseFacade;

import java.util.List;
import java.util.Date;

/**
 * This class implements a facade for all of the database layer's functionality.
 * @author Niels Joncheere
 */
public class SQLDatabaseFacade extends DatabaseFacade{
	private UserDatabase	userDb;
	private RegularDatabase	regularDb;
	private RawDatabase	rawDb;

	/**
	 * Creates a new database facade.
	 */
	public SQLDatabaseFacade(String dbUser, String dbPassword, String dbUrl) {
		userDb		= new UserDatabase(	dbUser,
							dbPassword,
							dbUrl);
		regularDb	= new RegularDatabase(	dbUser,
							dbPassword,
							dbUrl);
		rawDb		= new RawDatabase(	dbUser,
							dbPassword,
							dbUrl);
	}

	/**
	 * Inserts a new user profile into the user database.
	 */
	public void insert(UserProfile profile)
		throws SQLDatabaseException {
	
		userDb.insert(profile);
	}

	/**
	 * Updates an existing user profile in the user database.
	 */
	public void update(UserProfile profile)
		throws SQLDatabaseException {

		userDb.update(profile);
	}

	/**
	 * Returns the user with the specified username.
	 */
	public UserProfile findUser(String username)
		throws SQLDatabaseException {

		return userDb.findUser(username);
	}

	/**
	 * Checks whether a user with the specified username exists.
	 */
	public boolean userExists(String username)
		throws SQLDatabaseException {

		return userDb.userExists(username);
	}

	/**
	 * Returns a list containing all records of the given information type
	 * that match the given query string.
	 */
	public List findRecords(String informationType, String queryString)
		throws SQLDatabaseException {

		return regularDb.findRecords(informationType, queryString);
	}

	/**
	 * Returns a list containing all records of the given information type
	 * that were added after the given date.
	 */
	public List findRecordsFrom(String informationType, Date date)
		throws SQLDatabaseException {

		return regularDb.findRecordsFrom(informationType, date);
	}

	/**
	 * Adds a new regular data object to the regular database.
	 */
	public void add(RegularData rd)
		throws SQLDatabaseException {
	
		regularDb.add(rd);
	}

	/**
	 * Returns the number of records of the given information type in the
	 * regular database.
	 */
	public int getNumberOfRegularRecords(String informationType)
		throws SQLDatabaseException {

		return regularDb.getNumberOfRegularRecords(informationType);
	}

	/**
	 * Returns a list of all raw data.
	 */
	public List getRawData()
		throws SQLDatabaseException {

		return rawDb.getRawData();
	}

	/**
	 * Returns a specific raw data object.
	 */
	public RawData getRawData(int id)
		throws SQLDatabaseException {

		return rawDb.getRawData(id);
	}

	public void addRawData(RegularData rd)
		throws SQLDatabaseException {

		rawDb.addRawData(rd);
	}

	/**
	 * Deletes a raw data object.
	 */
	public void deleteRawData(RawData rd)
		throws SQLDatabaseException {

		rawDb.deleteRawData(rd);
	}

	/**
	 * Updates a raw data object.
	 */
	public void updateRawData(RawData rd)
		throws SQLDatabaseException {

		rawDb.updateRawData(rd);
	}

	/**
	 * Returns the number of records in the raw database.
	 */
	public int getNumberOfRawRecords()
		throws SQLDatabaseException {

		return rawDb.getNumberOfRawRecords();
	}
}
