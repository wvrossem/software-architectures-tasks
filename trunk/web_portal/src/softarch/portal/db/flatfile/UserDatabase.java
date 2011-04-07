
// ADDED by Wouter & Ken

package softarch.portal.db.flatfile;

import softarch.portal.data.CheapSubscription;
import softarch.portal.data.ExpensiveSubscription;
import softarch.portal.data.ExpertAdministrator;
import softarch.portal.data.ExternalAdministrator;
import softarch.portal.data.FreeSubscription;
import softarch.portal.data.Operator;
import softarch.portal.data.RegularAdministrator;
import softarch.portal.data.RegularUser;
import softarch.portal.data.UserProfile;
import softarch.portal.db.sql.SQLDatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * This class encapsulates the user database.
 * @author Niels Joncheere
 */
public class UserDatabase extends Database {
	protected String dbFreeSubscription = "freeuser";
	protected String dbCheapSubscription = "cheapuser";
	protected String dbExpensiveSubscription = "expensiveuser";
	
	/**
	 * Creates a new user database.
	 */
	public UserDatabase() {
		super();
		
		String fieldnames[] = {
				"UserName",
				"Password",
				"FirstName",
				"LastName",
				"EmailAddress",
				"LastLogin"
		};
		
		try {
			csvController.createDatabase(dbFreeSubscription, fieldnames);
			csvController.createDatabase(dbCheapSubscription, fieldnames);
			csvController.createDatabase(dbExpensiveSubscription, fieldnames);
		} catch (FlatFileDatabaseException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Inserts a new user profile into the user database.
	 */
	public void insert(UserProfile profile) {
		UserProfileCsvValues csvValues = new UserProfileCsvValues(profile);
		try {
			if(profile instanceof FreeSubscription) {
				csvController.insertProfile(dbFreeSubscription, csvValues);
			} else if(profile instanceof CheapSubscription) {
				csvController.insertProfile(dbCheapSubscription, csvValues);
			} else if(profile instanceof ExpensiveSubscription) {
				csvController.insertProfile(dbExpensiveSubscription, csvValues);
			} else {
				throw new FlatFileDatabaseException("Can't insert an invalid profile type.");
			}
		} catch (FlatFileDatabaseException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Updates an existing user profile in the user database.
	 */
	public void update(UserProfile profile) {		
		UserProfileCsvValues csvValues = new UserProfileCsvValues(profile);
		try {
			if(profile instanceof FreeSubscription) {
				csvController.updateProfile(dbFreeSubscription, csvValues);
			} else if(profile instanceof CheapSubscription) {
				csvController.updateProfile(dbCheapSubscription, csvValues);
			} else if(profile instanceof ExpensiveSubscription) {
				csvController.updateProfile(dbExpensiveSubscription, csvValues);
			} else {
				throw new FlatFileDatabaseException("Can't update an invalid profile type.");
			}
		} catch (FlatFileDatabaseException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Returns the user with the specified username.
	 * @throws SQLDatabaseException 
	 * @throws FlatFileDatabaseException 
	 */
	public UserProfile findUser(String userName) throws FlatFileDatabaseException {
		// We need to look at the last version of the results returned by the different select calls ...
		// As this application doesn't support deletion of an account,
		//  we can assume that once a profile is present, the account is still valid
		// To support deletion, eighter purge the csv file of all records for that specific profile (time costly),
		//  or add for example a record with username and null values for that profile, and look for that here
		
		List<UserProfile> results;
		UserProfile result = null;
		UserProfile latest = null;
		
		results = csvController.selectProfileWithUsername(dbFreeSubscription, userName);
		if(!results.isEmpty()) {
			result = results.get(results.size() - 1); // return the current version
			
			latest = result;
		}
		
		results = csvController.selectProfileWithUsername(dbCheapSubscription, userName);
		if(!results.isEmpty()) {
			result = results.get(results.size() - 1); // return the current version
			if(latest == null) {
				latest = result;
			} else if(latest.getLastLogin().before(result.getLastLogin())) {
				latest = result;
			}
		}
		
		results = csvController.selectProfileWithUsername(dbExpensiveSubscription, userName);
		if(!results.isEmpty()) {
			result = results.get(results.size() - 1); // return the current version
			if(latest == null) {
				latest = result;
			} else if(latest.getLastLogin().before(result.getLastLogin())) {
				latest = result;
			}
		}
		
		if(latest == null) {
			throw new FlatFileDatabaseException("Invalid username!");
		}
		
		return latest;
	}

	/**
	 * Checks whether a user with the specified username exists.
	 * @throws FlatFileDatabaseException 
	 */
	public boolean userExists(String userName) throws FlatFileDatabaseException {
		// As this application doesn't support deletion of an account,
		//  we can assume that once a profile is present, the account is still valid
		// To support deletion, eighter purge the csv file of all records for that specific profile (time costly),
		//  or add for example a record with username and null values for that profile, and look for that here
		//  ! keep the last logged values so when we recreate an account with a previously used account we don't
		//   fail to access the account.
		
		List<UserProfile> results;
		
		results = csvController.selectProfileWithUsername(dbFreeSubscription, userName);
		if(!results.isEmpty()) {
			return true;
		}
		
		results = csvController.selectProfileWithUsername(dbCheapSubscription, userName);
		if(!results.isEmpty()) {
			return true;
		}
		
		results = csvController.selectProfileWithUsername(dbExpensiveSubscription, userName);
		if(!results.isEmpty()) {
			return true;
		}
		
		return false;
	}
}
