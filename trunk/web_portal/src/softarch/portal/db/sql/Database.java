package softarch.portal.db.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.Iterator;
import java.util.Vector;

import softarch.portal.data.Article;
import softarch.portal.data.Book;
import softarch.portal.data.CheapSubscription;
import softarch.portal.data.Conference;
import softarch.portal.data.ExpensiveSubscription;
import softarch.portal.data.ExpertAdministrator;
import softarch.portal.data.ExternalAdministrator;
import softarch.portal.data.FreeSubscription;
import softarch.portal.data.InterestingWebsite;
import softarch.portal.data.Operator;
import softarch.portal.data.RawData;
import softarch.portal.data.RegularAdministrator;
import softarch.portal.data.Report;
import softarch.portal.data.SoftwareRepository;

/**
 * This abstract class implements the behaviour that is to be shared
 * by all databases.
 * @author Niels Joncheere
 */
public class Database {
	protected String dbUser;
	protected String dbPassword;
	protected String dbUrl;

	/**
	 * Creates a new database.
	 */
	public Database(String dbUser, String dbPassword, String dbUrl) {
		this.dbUser	= dbUser;
		this.dbPassword	= dbPassword;
		this.dbUrl	= dbUrl;
	}

	/**
	 * Executes the given SQL queries.
	 */
	public void executeSql(List queries)
		throws SQLDatabaseException {

		for (Iterator i = queries.iterator(); i.hasNext(); )
			executeSql((String) i.next());
	}
	
	public Connection getConnection() throws SQLDatabaseException, SQLException {
		// Load the HyperSQL JDBC driver:
		try {
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
		}
		catch (Exception e) {
			throw new SQLDatabaseException(
				"Unable to load the HyperSQL JDBC driver!");
		}
		
		Connection dbConnection = DriverManager.getConnection(
									"jdbc:hsqldb:" + dbUrl,
									dbUser,
									dbPassword);
		
		return dbConnection;
	}

	/**
	 * Executes the given SQL query.
	 * Note that no result will be returned.
	 */
	public void executeSql(String query)
		throws SQLDatabaseException {

		

		// Connect to the database:
		try {
			Connection dbConnection = getConnection();
			Statement statement = dbConnection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			statement.executeUpdate(query);

			statement.close();
			dbConnection.close();
		}

		// Exception handling:
		catch (SQLException e) {
			throw new SQLDatabaseException(
				"SQL Exception: " + e.getMessage());
		}
		catch (Exception e) {
			throw new SQLDatabaseException(
				"Unexpected Exception: " + e.getMessage());
		}
	}
	
	// MODIFIED by Wouter & Ken
	/**
	 * Removes illegal SQL characters from the given input string.
	 */
	protected String normalizeSql(String input) {
		String	result	= new String();
		int	length	= input.length();

		for (int i = 0; i < length; i++) {
			char c = input.charAt(i);

			switch (c) {
				case '\'':
					result += "\\\'";
					break;
				case '\"':
					result += "\\\"";
					break;
				default:
					result += c;
			}
		}
		return result;
	}
	
	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add the
	 * <i>raw data</i> object to a relational database.
	 */
	public List asSqlRawData(RawData rd) {
		List queries = new Vector();
		// The following check could be avoided by using a dummy
		// structure (whose asSql method returns the SQL INSERT
		// string below) until the raw data object is structured.
		if (rd.structure == null)
			queries.add("INSERT INTO Raw (ID, Source, Structured) VALUES (" + rd.id + ", \'" + normalizeSql(rd.source) + "\', 0);");
		else {
			queries.add("INSERT INTO Raw (ID, Source, Structured) VALUES (" + rd.id + ", \'" + normalizeSql(rd.source) + "\', 1);");
			if(rd.structure instanceof Article) {
				queries.add(asSqlArticle((Article)rd.structure, rd));
			} else if(rd.structure instanceof Book) {
				queries.add(asSqlBook((Book)rd.structure, rd));
			} else if(rd.structure instanceof Conference) {
				queries.add(asSqlConference((Conference)rd.structure, rd));
			} else if(rd.structure instanceof InterestingWebsite) {
				queries.add(asSqlInterestingWebsite((InterestingWebsite)rd.structure, rd));
			} else if(rd.structure instanceof Report) {
				queries.add(asSqlReport((Report)rd.structure, rd));
			} else if(rd.structure instanceof SoftwareRepository) {
				queries.add(asSqlSoftwareRepository((SoftwareRepository)rd.structure, rd));
			}
		}
		return queries;
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL DELETE string that allows the system to delete the
	 * <i>raw data</i> object from a relational database.
	 */
	public String asSqlDeleteRawData(RawData rd) {
		// The following check could be avoided by using a dummy
		// structure (whose asSqlDelete method returns the SQL DELETE
		// string below) until the raw data object is structured.
		if (rd.structure == null)
			return "DELETE FROM Raw WHERE ID = " + rd.id + ";";
		else
			if(rd.structure instanceof Article) {
				return asSqlDeleteArticle((Article)rd.structure, rd);
			} else if(rd.structure instanceof Book) {
				return asSqlDeleteBook((Book)rd.structure, rd);
			} else if(rd.structure instanceof Conference) {
				return asSqlDeleteConference((Conference)rd.structure, rd);
			} else if(rd.structure instanceof InterestingWebsite) {
				return asSqlDeleteInterestingWebsite((InterestingWebsite)rd.structure, rd);
			} else if(rd.structure instanceof Report) {
				return asSqlDeleteReport((Report)rd.structure, rd);
			} else if(rd.structure instanceof SoftwareRepository) {
				return asSqlDeleteSoftwareRepository((SoftwareRepository)rd.structure, rd);
			} else {
				return "";
			}
	}
	
	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add the
	 * article to a relational database.
	 */
	public String asSqlArticle(Article a) {
		return	"INSERT INTO Article (DateAdded, Author, " +
			"PublicationDate, Review, Summary, Title) VALUES (\'" +
			a.df.format(a.dateAdded) + "\', \'" + normalizeSql(a.author) +
			"\', \'" + a.df.format(a.publicationDate) + "\', \'" +
			normalizeSql(a.review) + "\', \'" +
			normalizeSql(a.summary) + "\', \'" + normalizeSql(a.title) +
			"\');";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add a
	 * <code>RawData</code> object with an <code>Article</code>
	 * structure to a relational database.
	 * @see softarch.portal.data.RawData
	 */
	public String asSqlArticle(Article a, RawData rd) {
		return	"INSERT INTO RawArticle (ID, DateAdded, Author, " +
			"PublicationDate, Review, Summary, Title) VALUES (" +
			rd.getId() + ", \'" +
			a.df.format(a.dateAdded) + "\', \'" + normalizeSql(a.author) +
			"\', \'" + a.df.format(a.publicationDate) + "\', \'" +
			normalizeSql(a.review) + "\', \'" +
			normalizeSql(a.summary) + "\', \'" + normalizeSql(a.title) +
			"\');";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL DELETE string that allows the system to delete a
	 * <code>RawData</code> object with an <code>Article</code>
	 * structure from a relational database.
	 * @see softarch.portal.data.RawData
	 */
	public String asSqlDeleteArticle(Article a, RawData rd) {
		return "DELETE FROM RawArticle WHERE ID = " + rd.getId() + ";";
	}
	
	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add the
	 * book to a relational database.
	 */
	public String asSqlBook(Book b) {
		return	"INSERT INTO Book (DateAdded, Author, ISBN, Pages, " +
			"PublicationDate, Publisher, Review, Summary, Title) " +
			"VALUES " + "(\'" + b.df.format(b.dateAdded) + "\', \'" +
			normalizeSql(b.author) + "\', " + b.isbn + ", " + b.pages +
			", \'" + b.df.format(b.publicationDate) + "\', \'" +
			normalizeSql(b.publisher) + "\', \'" +
			normalizeSql(b.review) + "\', \'" +
			normalizeSql(b.summary) + "\', \'" + normalizeSql(b.title) +
			"\');";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add a
	 * <code>RawData</code> object with a <code>Book</code>
	 * structure to a relational database.
	 * @see softarch.portal.data.RawData
	 */
	public String asSqlBook(Book b, RawData rd) {
		return	"INSERT INTO RawBook (ID, DateAdded, Author, " +
			"ISBN, Pages, PublicationDate, Publisher, Review, " +
			"Summary, Title) VALUES (" + rd.getId() + ", \'" +
			b.df.format(b.dateAdded) + "\', \'" + normalizeSql(b.author) +
			"\', " + b.isbn + ", " + b.pages + ", \'" +
			b.df.format(b.publicationDate) + "\', \'" +
			normalizeSql(b.publisher) + "\', \'" +
			normalizeSql(b.review) + "\', \'" +
			normalizeSql(b.summary) + "\', \'" +
			normalizeSql(b.title) + "\');";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL DELETE string that allows the system to delete a
	 * <code>RawData</code> object with an <code>Book</code>
	 * structure from a relational database.
	 * @see softarch.portal.data.RawData
	 */
	public String asSqlDeleteBook(Book b, RawData rd) {
		return "DELETE FROM RawBook WHERE ID = " + rd.getId() + ";";
	}
	
	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add the
	 * conference to a relational database.
	 */
	public String asSqlConference(Conference c) {
		return	"INSERT INTO Conference (DateAdded, Date, Location, " +
			"Name, Review, Summary, URL) VALUES (\'" +
			c.df.format(c.dateAdded) + "\', \'" + c.df.format(c.date) +
			"\', \'" + normalizeSql(c.location) + "\', \'" +
			normalizeSql(c.name) + "\', \'" + normalizeSql(c.review) +
			"\', \'" + normalizeSql(c.summary) + "\', \'" +
			normalizeSql(c.url.toString()) + "\');";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add a
	 * <code>RawData</code> object with a <code>Conference</code>
	 * structure to a relational database.
	 * @see softarch.portal.data.RawData
	 */
	public String asSqlConference(Conference c, RawData rd) {
		return	"INSERT INTO RawConference (ID, DateAdded, Date, " +
			"Location, Name, Review, Summary, URL) VALUES (" +
			rd.getId() + ", \'" +
			c.df.format(c.dateAdded) + "\', \'" + c.df.format(c.date) +
			"\', \'" + normalizeSql(c.location) + "\', \'" +
			normalizeSql(c.name) + "\', \'" + normalizeSql(c.review) +
			"\', \'" + normalizeSql(c.summary) + "\', \'" +
			normalizeSql(c.url.toString()) + "\');";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL DELETE string that allows the system to delete a
	 * <code>RawData</code> object with a <code>Conference</code>
	 * structure from a relational database.
	 * @see softarch.portal.data.RawData
	 */
	public String asSqlDeleteConference(Conference c, RawData rd) {
		return	"DELETE FROM RawConference WHERE ID = " + rd.getId() +
			";";
	}
	
	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add the
	 * interesting website to a relational database.
	 */
	public String asSqlInterestingWebsite(InterestingWebsite i) {
		return	"INSERT INTO InterestingWebsite (DateAdded, Author, " +
			"Review, Summary, Title, URL) " + "VALUES (\'" +
			i.df.format(i.dateAdded) + "\', \'" + normalizeSql(i.author) +
			"\', \'" + normalizeSql(i.review) + "\', \'" + 
			normalizeSql(i.summary) + "\', \'" + normalizeSql(i.title) +
			"\', \'" + normalizeSql(i.url.toString()) + "\');";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add a
	 * <code>RawData</code> object with an <code>InterestingWebsite</code>
	 * structure to a relational database.
	 * @see softarch.portal.data.RawData
	 */
	public String asSqlInterestingWebsite(InterestingWebsite i, RawData rd) {
		return	"INSERT INTO RawInterestingWebsite (ID, " +
			"DateAdded, Author, Review, Summary, Title, URL) " +
			"VALUES (" + rd.getId() + ", \'" +
			i.df.format(i.dateAdded) + "\', \'" + normalizeSql(i.author) +
			"\', \'" + normalizeSql(i.review) + "\', \'" +
			normalizeSql(i.summary) + "\', \'" + normalizeSql(i.title) +
			"\', \'" + normalizeSql(i.url.toString()) + "\');";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL DELETE string that allows the system to delete a
	 * <code>RawData</code> object with an <code>InterestingWebsite</code>
	 * structure from a relational database.
	 * @see softarch.portal.data.RawData
	 */
	public String asSqlDeleteInterestingWebsite(InterestingWebsite i, RawData rd) {
		return	"DELETE FROM RawInterestingWebsite WHERE ID = " +
			rd.getId() + ";";
	}
	
	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add the
	 * report to a relational database.
	 */
	public String asSqlReport(Report r) {
		return	"INSERT INTO Report (DateAdded, Author, " +
			"PublicationDate, Review, Summary, Title) VALUES (\'" +
			r.df.format(r.dateAdded) + "\', \'" + normalizeSql(r.author) +
			"\', \'" + r.publicationDate + "\', \'" +
			normalizeSql(r.review) + "\', \'" +
			normalizeSql(r.summary) + "\', \'" + normalizeSql(r.title) +
			"\');";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add a
	 * <code>RawData</code> object with a <code>Report</code>
	 * structure to a relational database.
	 * @see softarch.portal.data.RawData
	 */
	public String asSqlReport(Report r, RawData rd) {
		return	"INSERT INTO RawReport (ID, DateAdded, Author, " +
			"PublicationDate, Review, Summary, Title) VALUES (" +
			rd.getId() + ", \'" + r.df.format(r.dateAdded) + "\', \'" +
			normalizeSql(r.author) + "\', \'" +
			r.df.format(r.publicationDate) + "\', \'" +
			normalizeSql(r.review) + "\', \'" +
			normalizeSql(r.summary) + "\', \'" + normalizeSql(r.title) +
			"\');";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL DELETE string that allows the system to delete a
	 * <code>RawData</code> object with a <code>Report</code>
	 * structure from a relational database.
	 * @see softarch.portal.data.RawData
	 */
	public String asSqlDeleteReport(Report r, RawData rd) {
		return "DELETE FROM RawReport WHERE ID = " + rd.getId() + ";";
	}
	
	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add the
	 * software repository to a relational database.
	 */
	public String asSqlSoftwareRepository(SoftwareRepository s) {
		return	"INSERT INTO SoftwareRepository (DateAdded, Author, " +
			"Name, URL) VALUES (\'" + s.df.format(s.dateAdded) +
			"\', \'" + normalizeSql(s.author) + "\', \'" +
			normalizeSql(s.name) + "\', \'" +
			normalizeSql(s.url.toString()) + "\');";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add a
	 * <code>RawData</code> object with a <code>SoftwareRepository</code>
	 * structure to a relational database.
	 * @see softarch.portal.data.RawData
	 */
	public String asSqlSoftwareRepository(SoftwareRepository s, RawData rd) {
		return	"INSERT INTO RawSoftwareRepository (ID, " +
			"DateAdded, Author, Name, URL) VALUES (\'" +
			rd.getId() + "\', \'" +
			s.df.format(s.dateAdded) + "\', \'" +
			normalizeSql(s.author) + "\', \'" + normalizeSql(s.name) +
			"\', \'" + normalizeSql(s.url.toString()) + "\');";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL DELETE string that allows the system to delete a
	 * <code>RawData</code> object with a <code>SoftwareRepository</code>
	 * structure from a relational database.
	 * @see softarch.portal.data.RawData
	 */
	public String asSqlDeleteSoftwareRepository(SoftwareRepository s, RawData rd) {
		return	"DELETE FROM RawSoftwareRepository WHERE ID = " +
			rd.getId() + ";";
	}
	
	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add
	 * the account to a relational database.
	 */
	public String asSqlCheapSubscription(CheapSubscription c) {
		return	"INSERT INTO CheapSubscription (Username, Password, " +
			"FirstName, LastName, EmailAddress, LastLogin) " +
			"VALUES (\'" + normalizeSql(c.username) + "\', \'" +
			normalizeSql(c.password) + "\', \'" +
			normalizeSql(c.firstName) + "\', \'" +
			normalizeSql(c.lastName) + "\', \'" +
			normalizeSql(c.emailAddress) + "\', \'" +
			c.df.format(c.lastLogin) + "\');";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL UPDATE string that allows the system to update
	 * the account in a relational database.
	 */
	public String asSqlUpdateCheapSubscription(CheapSubscription c) {
		return  "UPDATE CheapSubscription SET Password = \'" +
			normalizeSql(c.password) + "\', FirstName = \'" +
	                normalizeSql(c.firstName) + "\', LastName = \'" +
			normalizeSql(c.lastName) + "\', EmailAddress = \'" +
	                normalizeSql(c.emailAddress) + "\', LastLogin = \'" +
			c.df.format(c.lastLogin) + "\' " + "WHERE Username = \'" +
			normalizeSql(c.username) + "\';";
	}
	
	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add
	 * the account to a relational database.
	 */
	public String asSqlExpensiveSubscription(ExpensiveSubscription e) {
		return	"INSERT INTO ExpensiveSubscription (Username, " +
			"Password, FirstName, LastName, EmailAddress, " +
			"LastLogin) VALUES (\'" + normalizeSql(e.username) +
			"\', \'" + normalizeSql(e.password) +"\', \'" +
			normalizeSql(e.firstName) + "\', \'" +
			normalizeSql(e.lastName) + "\', \'" +
			normalizeSql(e.emailAddress) + "\', \'" +
			e.df.format(e.lastLogin) + "\');";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL UPDATE string that allows the system to update
	 * the account in a relational database.
	 */
	public String asSqlUpdateExpensiveSubscription(ExpensiveSubscription e) {
		return	"UPDATE ExpensiveSubscription SET Password = \'" +
			normalizeSql(e.password) + "\', FirstName = \'" +
	                normalizeSql(e.firstName) + "\', LastName = \'" +
			normalizeSql(e.lastName) + "\', EmailAddress = \'" +
	                normalizeSql(e.emailAddress) + "\', LastLogin = \'" +
			e.df.format(e.lastLogin) + "\' " + "WHERE Username = \'" +
			normalizeSql(e.username) + "\';";
	}
	
	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add
	 * the account to a relational database.
	 */
	public String asSqlExpertAdministrator(ExpertAdministrator e) {
		return	"INSERT INTO ExpertAdministrator (Username, " +
			"Password, FirstName, LastName, EmailAddress, " +
			"LastLogin) VALUES (\'" + normalizeSql(e.username) +
			"\', \'" + normalizeSql(e.password) +"\', \'" +
			normalizeSql(e.firstName) + "\', \'" +
			normalizeSql(e.lastName) + "\', \'" +
			normalizeSql(e.emailAddress) + "\', \'" +
			e.df.format(e.lastLogin) + "\');";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL UPDATE string that allows the system to update
	 * the account in a relational database.
	 */
	public String asSqlUpdateExpertAdministrator(ExpertAdministrator e) {
		return  "UPDATE ExpertAdministrator SET Password = \'" +
			normalizeSql(e.password) + "\', FirstName = \'" +
			normalizeSql(e.firstName) + "\', LastName = \'" +
			normalizeSql(e.lastName) + "\', EmailAddress = \'" +
			normalizeSql(e.emailAddress) + "\', LastLogin = \'" +
			e.df.format(e.lastLogin) + "\' " + "WHERE Username = \'" +
			normalizeSql(e.username) + "\';";
	}
	
	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add
	 * the account to a relational database.
	 */
	public String asSqlFreeSubscription(FreeSubscription f) {
		return	"INSERT INTO FreeSubscription (Username, Password, " +
			"FirstName, LastName, EmailAddress, LastLogin) " +
			"VALUES (\'" + normalizeSql(f.username) + "\', \'" +
			normalizeSql(f.password) + "\', \'" +
			normalizeSql(f.firstName) + "\', \'" +
			normalizeSql(f.lastName) + "\', \'" +
			normalizeSql(f.emailAddress) + "\', \'" +
			f.df.format(f.lastLogin) + "\');";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL UPDATE string that allows the system to update
	 * the account in a relational database.
	 */
	public String asSqlUpdateFreeSubscription(FreeSubscription f) {
		return	"UPDATE FreeSubscription SET Password = \'" +
			normalizeSql(f.password) + "\', FirstName = \'" +
			normalizeSql(f.firstName) + "\', LastName = \'" +
			normalizeSql(f.lastName) + "\', EmailAddress = \'" +
			normalizeSql(f.emailAddress) + "\', LastLogin = \'" +
			f.df.format(f.lastLogin) + "\' " + "WHERE Username = \'" +
			normalizeSql(f.username) + "\';";
	}
	
	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add
	 * the account to a relational database.
	 */
	public String asSqlOperator(Operator o) {
		return	"INSERT INTO Operator (Username, Password, " +
			"FirstName, LastName, EmailAddress, LastLogin) " +
			"VALUES (\'" + normalizeSql(o.username) + "\', \'" +
			normalizeSql(o.password) + "\', \'" +
			normalizeSql(o.firstName) + "\', \'" +
			normalizeSql(o.lastName) + "\', \'" +
			normalizeSql(o.emailAddress) + "\', \'" +
			o.df.format(o.lastLogin) + "\');";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL UPDATE string that allows the system to update
	 * the account in a relational database.
	 */
	public String asSqlUpdateOperator(Operator o) {
		return  "UPDATE Operator SET Password = \'" + 
			normalizeSql(o.password) + "\', FirstName = \'" +
			normalizeSql(o.firstName) + "\', LastName = \'" +
			normalizeSql(o.lastName) + "\', EmailAddress = \'" +
			normalizeSql(o.emailAddress) + "\', LastLogin = \'" +
			o.df.format(o.lastLogin) + "\' " + "WHERE Username = \'" +
			normalizeSql(o.username) + "\';";
	}
	
	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add
	 * the account to a relational database.
	 */
	public String asSqlRegularAdministrator(RegularAdministrator r) {
		return	"INSERT INTO RegularAdministrator (Username, " +
			"Password, FirstName, LastName, EmailAddress, " +
			"LastLogin) VALUES (\'" + normalizeSql(r.username) +
			"\', \'" + normalizeSql(r.password) +"\', \'" +
			normalizeSql(r.firstName) + "\', \'" +
			normalizeSql(r.lastName) + "\', \'" +
			normalizeSql(r.emailAddress) + "\', \'" + 
			r.df.format(r.lastLogin) + "\');";
	}

	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL UPDATE string that allows the system to update
	 * the account in a relational database.
	 */
	public String asSqlUpdateRegularAdministrator(RegularAdministrator r) {
		return  "UPDATE RegularAdministrator SET Password = \'" +
			normalizeSql(r.password) + "\', FirstName = \'" +
			normalizeSql(r.firstName) + "\', LastName = \'" +
			normalizeSql(r.lastName) + "\', EmailAddress = \'" +
			normalizeSql(r.emailAddress) + "\', LastLogin = \'" +
			r.df.format(r.lastLogin) + "\' " + "WHERE Username = \'" +
			normalizeSql(r.username) + "\';";
	}
	
	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL INSERT string that allows the system to add
	 * the account to a relational database.
	 */
	public String asSqlExternalAdministrator(ExternalAdministrator e) {
		return	"INSERT INTO ExternalAdministrator (Username, " +
			"Password, FirstName, LastName, EmailAddress, " +
			"LastLogin) VALUES (\'" + normalizeSql(e.username) +
			"\', \'" + normalizeSql(e.password) +"\', \'" +
			normalizeSql(e.firstName) + "\', \'" +
			normalizeSql(e.lastName) + "\', \'" +
			normalizeSql(e.emailAddress) + "\', \'" +
			e.df.format(e.lastLogin) + "\');";
	}
	
	// MODIFIED by Wouter & Ken
	/**
	 * Returns an SQL UPDATE string that allows the system to update
	 * the account in a relational database.
	 */
	public String asSqlUpdateExternalAdministrator(ExternalAdministrator e) {
		return  "UPDATE ExternalAdministrator SET Password = \'" +
			normalizeSql(e.password) + "\', FirstName = \'" +
			normalizeSql(e.firstName) + "\', LastName = \'" +
			normalizeSql(e.lastName) + "\', EmailAddress = \'" +
			normalizeSql(e.emailAddress) + "\', LastLogin = \'" +
			e.df.format(e.lastLogin) + "\' " + "WHERE Username = \'" +
			normalizeSql(e.username) + "\';";
	}
}
