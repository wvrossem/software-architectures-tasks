package softarch.portal.data;

import java.util.Date;

/**
 * This is an abstract superclass for all user profiles.
 * @author Niels Joncheere
 */
public abstract class UserProfile extends Data {
	
	// MODIFIED by Wouter & Ken
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public	String	username;
	public	String	password;
	public	String	firstName;
	public	String	lastName;
	public	String	emailAddress;
	public	Date	lastLogin;

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL string that allows the system to add the account
	 * to a relational database.
	 */
	//public abstract String asSql();

	/**
	 * When a user has logged in successfully, he will be
	 * redirected to this page.
	 */
	public abstract String getDefaultPage();

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL UPDATE string that allows the system to update
	 * the account in a relational database.
	 */
	//public abstract String asSqlUpdate();

	// MODIFIED by Wouter & Ken
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public UserProfile setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
		return this;
	}
}
