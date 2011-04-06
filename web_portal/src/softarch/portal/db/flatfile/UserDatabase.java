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
			if(profile instanceof FreeSubscription ) {
				csvController.insertProfile(dbFreeSubscription, csvValues);
			} else if(profile instanceof CheapSubscription) {
				csvController.insertProfile(dbCheapSubscription, csvValues);
			} else if(profile instanceof ExpensiveSubscription) {
				csvController.insertProfile(dbExpensiveSubscription, csvValues);
			}
			throw new FlatFileDatabaseException("Invalid profile type.");
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
			if(profile instanceof FreeSubscription ) {
				csvController.updateProfile(dbFreeSubscription, csvValues);
			} else if(profile instanceof CheapSubscription) {
				csvController.updateProfile(dbCheapSubscription, csvValues);
			} else if(profile instanceof ExpensiveSubscription) {
				csvController.updateProfile(dbExpensiveSubscription, csvValues);
			}
			throw new FlatFileDatabaseException("Invalid profile type.");
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
		List<UserProfile> result;
		
		result = csvController.selectProfileWithUsername("FreeSubscription", userName);
		if(!result.isEmpty()) {
			return result.get(0);
		}
		
		result = csvController.selectProfileWithUsername("CheapSubscription", userName);
		if(!result.isEmpty()) {
			return result.get(0);
		}
		
		result = csvController.selectProfileWithUsername("ExpensiveSubscription", userName);
		if(!result.isEmpty()) {
			return result.get(0);
		}
		
		throw new FlatFileDatabaseException("Invalid username!");
	}

	/**
	 * Checks whether a user with the specified username exists.
	 * @throws FlatFileDatabaseException 
	 */
	public boolean userExists(String userName) throws FlatFileDatabaseException {
		List<UserProfile> result;
		
		result = csvController.selectProfileWithUsername("FreeSubscription", userName);
		if(!result.isEmpty()) {
			return true;
		}
		
		result = csvController.selectProfileWithUsername("CheapSubscription", userName);
		if(!result.isEmpty()) {
			return true;
		}
		
		result = csvController.selectProfileWithUsername("ExpensiveSubscription", userName);
		if(!result.isEmpty()) {
			return true;
		}
		
		return false;
	}
}
