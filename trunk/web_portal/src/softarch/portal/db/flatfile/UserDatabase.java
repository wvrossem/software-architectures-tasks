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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * This class encapsulates the user database.
 * @author Niels Joncheere
 */
public class UserDatabase extends Database {
	/**
	 * Creates a new user database.
	 */
	public UserDatabase() {
		super("users");
		
		String fieldnames[] = {
				"UserName",
				"Password",
				"FirstName",
				"LastName",
				"EmailAddress",
				"LastLogin"
		};
		
		try {
			csvController.createDatabase("users", fieldnames);
		} catch (DatabaseException e) {
			
		}
	}

	/**
	 * Inserts a new user profile into the user database.
	 */
	public void insert(UserProfile profile) {
		UserProfileCsvValues csvValues = new UserProfileCsvValues(profile);
		insert(csvValues);	
	}

	/**
	 * Updates an existing user profile in the user database.
	 */
	public void update(UserProfile profile) {		
		UserProfileCsvValues csvValues = new UserProfileCsvValues(profile);
		update(csvValues);
	}

	/**
	 * Returns the user with the specified username.
	 */
	public UserProfile findUser(String userName) {
		try {
			return find(userName);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Checks whether a user with the specified username exists.
	 * @throws DatabaseException 
	 */
	public boolean userExists(String userName) throws DatabaseException {
		return exists(userName);
	}

	public UserProfile find(String userName) throws DatabaseException, ParseException {
		CsvValues csvValues = csvController.find(dbName, "UserName", userName);
		
		// TODO Check subscription type
		@SuppressWarnings("deprecation")
		UserProfile userProfile = new CheapSubscription(
				(String) csvValues.get(0), 
				(String) csvValues.get(1), 
				(String) csvValues.get(2), 
				(String) csvValues.get(3), 
				(String) csvValues.get(4), 
				new SimpleDateFormat("E MMM d HH:mm:ss z yyyy", new Locale("en"))
					.parse((String) csvValues.get(5)));
		
		return userProfile;
	}

	public boolean exists(String userName) throws DatabaseException {
		return csvController.exists(dbName, "UserName", userName);
	}
	
	
}
