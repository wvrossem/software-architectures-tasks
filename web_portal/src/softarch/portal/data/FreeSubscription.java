package softarch.portal.data;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

/**
 * Represents a <i>free subscription</i> user account.
 * @author Niels Joncheere
 */
public class FreeSubscription extends RegularUser {
	/**
	 * Creates a new <i>free subscription</i> account from a
	 * <code>javax.servlet.http.HttpServletRequest</code> object.
	 * @see javax.servlet.http.HttpServletRequest
	 */
	public FreeSubscription(HttpServletRequest request) {
		this(	request.getParameter("Username"),
			request.getParameter("Password"),
			request.getParameter("FirstName"),
			request.getParameter("LastName"),
			request.getParameter("EmailAddress"),
			new Date());
	}

	/**
	 * Creates a new <i>free subscription</i> account from a
	 * <code>java.sql.ResultSet</code> object.
	 * @see java.sql.ResultSet
	 */
	public FreeSubscription(ResultSet rs)
		throws SQLException, ParseException {

		this.username		= rs.getString("Username");
		this.password		= rs.getString("Password");
		this.firstName		= rs.getString("FirstName");
		this.lastName		= rs.getString("LastName");
		this.emailAddress	= rs.getString("EmailAddress");
		this.lastLogin		= df.parse(rs.getString("LastLogin"));
	}

	/**
	 * Creates a new <i>free subscription</i> account.
	 */
	public FreeSubscription(	String	username,
					String	password,
					String	firstName,
					String	lastName,
					String	emailAddress,
					Date	lastLogin) {

		this.username		= username;
		this.password		= password;
		this.firstName		= firstName;
		this.lastName		= lastName;
		this.emailAddress	= emailAddress;
		this.lastLogin		= lastLogin;
	}

	/**
	 * Returns an XML representation of the object.
	 */
	public String asXml() {
		return	"<FreeSubscription>" +
			"<username>" + normalizeXml(username) + "</username>" +
			// password is not returned,
			// as it should only be used internally
			"<firstName>" +
			normalizeXml(firstName) + 
			"</firstName>" +
			"<lastName>" + normalizeXml(lastName) + "</lastName>" +
			"<emailAddress>" + 
			normalizeXml(emailAddress) +
			"</emailAddress>" +
			"<lastLogin>" + df.format(lastLogin) + "</lastLogin>" +
			"</FreeSubscription>";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add
	 * the account to a relational database.
	 */
	/*public String asSql() {
		return	"INSERT INTO FreeSubscription (Username, Password, " +
			"FirstName, LastName, EmailAddress, LastLogin) " +
			"VALUES (\'" + normalizeSql(username) + "\', \'" +
			normalizeSql(password) + "\', \'" +
			normalizeSql(firstName) + "\', \'" +
			normalizeSql(lastName) + "\', \'" +
			normalizeSql(emailAddress) + "\', \'" +
			df.format(lastLogin) + "\');";
	}*/

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL UPDATE string that allows the system to update
	 * the account in a relational database.
	 */
	/*public String asSqlUpdate() {
		return	"UPDATE FreeSubscription SET Password = \'" +
			normalizeSql(password) + "\', FirstName = \'" +
			normalizeSql(firstName) + "\', LastName = \'" +
			normalizeSql(lastName) + "\', EmailAddress = \'" +
			normalizeSql(emailAddress) + "\', LastLogin = \'" +
			df.format(lastLogin) + "\' " + "WHERE Username = \'" +
			normalizeSql(username) + "\';";
	}*/
}
