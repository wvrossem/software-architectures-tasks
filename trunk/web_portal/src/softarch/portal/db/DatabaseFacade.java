package softarch.portal.db;

import java.util.Date;
import java.util.List;

import softarch.portal.data.RawData;
import softarch.portal.data.RegularData;
import softarch.portal.data.UserProfile;
import softarch.portal.db.DatabaseException;
import softarch.portal.db.flatfile.FlatFileDatabaseException;
import softarch.portal.db.sql.SQLDatabaseException;

public abstract class DatabaseFacade {
	
	/**
	 * Inserts a new user profile into the user database.
	 * @throws FlatFileDatabaseException 
	 * @throws SQLDatabaseException 
	 */
	public abstract void insert(UserProfile profile) throws FlatFileDatabaseException, SQLDatabaseException;

	/**
	 * Updates an existing user profile in the user database.
	 * @throws FlatFileDatabaseException 
	 * @throws SQLDatabaseException 
	 */
	public abstract void update(UserProfile profile) throws FlatFileDatabaseException, SQLDatabaseException;

	/**
	 * Returns the user with the specified username.
	 * @throws FlatFileDatabaseException 
	 * @throws SQLDatabaseException 
	 */
	public abstract UserProfile findUser(String username) throws FlatFileDatabaseException, SQLDatabaseException;

	/**
	 * Checks whether a user with the specified username exists.
	 * @throws FlatFileDatabaseException 
	 * @throws SQLDatabaseException 
	 */
	public abstract boolean userExists(String username) throws FlatFileDatabaseException, SQLDatabaseException;

	/**
	 * Returns a list containing all records of the given information type
	 * that match the given query string.
	 * @throws FlatFileDatabaseException 
	 * @throws SQLDatabaseException 
	 */
	public abstract List findRecords(String informationType, String queryString) throws FlatFileDatabaseException, SQLDatabaseException;

	/**
	 * Returns a list containing all records of the given information type
	 * that were added after the given date.
	 * @throws FlatFileDatabaseException 
	 * @throws SQLDatabaseException 
	 */
	public abstract List findRecordsFrom(String informationType, Date date) throws FlatFileDatabaseException, SQLDatabaseException;

	/**
	 * Adds a new regular data object to the regular database.
	 * @throws FlatFileDatabaseException 
	 * @throws SQLDatabaseException 
	 */
	public abstract void add(RegularData rd) throws FlatFileDatabaseException, SQLDatabaseException;
	/**
	 * Returns the number of records of the given information type in the
	 * regular database.
	 * @throws FlatFileDatabaseException 
	 * @throws SQLDatabaseException 
	 */
	public abstract int getNumberOfRegularRecords(String informationType) throws FlatFileDatabaseException, SQLDatabaseException;

	/**
	 * Returns a list of all raw data.
	 * @throws FlatFileDatabaseException 
	 * @throws SQLDatabaseException 
	 */
	public abstract List getRawData() throws FlatFileDatabaseException, SQLDatabaseException;

	/**
	 * Returns a specific raw data object.
	 * @throws FlatFileDatabaseException 
	 * @throws SQLDatabaseException 
	 */
	public abstract RawData getRawData(int id) throws FlatFileDatabaseException, SQLDatabaseException;

	public abstract void addRawData(RegularData rd) throws FlatFileDatabaseException, SQLDatabaseException;

	/**
	 * Deletes a raw data object.
	 * @throws FlatFileDatabaseException 
	 * @throws SQLDatabaseException 
	 */
	public abstract void deleteRawData(RawData rd) throws FlatFileDatabaseException, SQLDatabaseException;

	/**
	 * Updates a raw data object.
	 * @throws FlatFileDatabaseException 
	 * @throws SQLDatabaseException 
	 */
	public abstract void updateRawData(RawData rd) throws FlatFileDatabaseException, SQLDatabaseException;

	/**
	 * Returns the number of records in the raw database.
	 * @throws FlatFileDatabaseException 
	 * @throws SQLDatabaseException 
	 */
	public abstract int getNumberOfRawRecords() throws FlatFileDatabaseException, SQLDatabaseException;

}
