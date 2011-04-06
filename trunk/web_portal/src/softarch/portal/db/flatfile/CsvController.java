package softarch.portal.db.flatfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import softarch.portal.data.CheapSubscription;
import softarch.portal.data.ExpensiveSubscription;
import softarch.portal.data.FreeSubscription;
import softarch.portal.data.UserProfile;


import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

public class CsvController {
	
	private boolean databaseExisits(String database) {
		return new File(database+".csv").exists();
	}
	
	public void createDatabase(String database, String[] fieldnames) throws FlatFileDatabaseException {
		try {
			if(!databaseExisits(database)) {
				CsvWriter csvOutput = new CsvWriter(new FileWriter(database+".csv", true), ',');
				
				for(String fieldname : fieldnames) {
					csvOutput.write(fieldname);
				}
				csvOutput.endRecord();
				
				csvOutput.close();
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void insertProfile(String database, CsvValues csvValues) throws FlatFileDatabaseException {
		try {
			if(!databaseExisits(database)) {
				throw new FlatFileDatabaseException("Database does not exist");
			}
			
			if(!selectProfileWithUsername(database, (String)csvValues.get(0)).isEmpty()) {
				throw new FlatFileDatabaseException("Record already exists.");
			}

			CsvWriter csvOutput = new CsvWriter(new FileWriter(database+".csv", true), ',');	
			
			for(Object el : csvValues.getValues()) {
				csvOutput.write(el.toString());
			}
			csvOutput.endRecord();
			
			csvOutput.close(); 
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void updateProfile(String database, CsvValues csvValues) throws FlatFileDatabaseException {
		// The csv file might have multple lines with the same username:
		//  - usrname is considered to be unique for a profile
		//  - lines with the same username are considered different versions of the profile
		//  - we add a line to the csv with the most recent version
		//  - this approach has several advantages:
		//		- no need to completely rewrite the csv file after each update (speed considerations)
		//		- we have complete history information of this user profile
		//		- this way we could add a functionality like restore previous versions
		
		try {
			if(!databaseExisits(database)) {
				throw new FlatFileDatabaseException("Database does not exist");
			}
			
			List<UserProfile> result = selectProfileWithUsername(database, (String)csvValues.get(0));
			if(result.isEmpty()) {
				throw new FlatFileDatabaseException("Record does not exist");
			}
			
			CsvWriter csvOutput = new CsvWriter(new FileWriter(database+".csv", true), ',');
			
			for(Object el : csvValues.getValues()) {
				csvOutput.write(el.toString());
			}
			csvOutput.endRecord();
			
			csvOutput.close();
		} catch(IOException e) {
			e.printStackTrace();
		}	
	}
	
	public List<UserProfile> selectProfileWithUsername(String database, String userName) {
		// The csv file might have multiple lines with the same username:
		//  - username is considered to be unique for a profile
		//  - lines with the same username are considered different versions of the profile
		//  - we return a list with all the versions (earliest to most recent)
		
		List<UserProfile> result = new ArrayList<UserProfile>();
		
		try {
			if(!databaseExisits(database)) {
				throw new FlatFileDatabaseException("Database does not exist");
			}
			
			CsvReader db = new CsvReader(database+".csv");
			
			db.readHeaders();
			while(db.readRecord()) {
				if(db.get("UserName").equals(userName)) {
					if(database.equals("freeuser")) {
						result.add(new FreeSubscription(
								db.get("UserName"),
								db.get("Password"),
								db.get("FirstName"),
								db.get("LastName"),
								db.get("EmailAddress"),
								new SimpleDateFormat("E MMM d HH:mm:ss z yyyy", new Locale("en"))
								.parse((String) db.get("LastLogin"))
							));
					} else if(database.equals("cheapuser")) {
						result.add(new CheapSubscription(
								db.get("UserName"),
								db.get("Password"),
								db.get("FirstName"),
								db.get("LastName"),
								db.get("EmailAddress"),
								new SimpleDateFormat("E MMM d HH:mm:ss z yyyy", new Locale("en"))
								.parse((String) db.get("LastLogin"))
							));					
					} else if(database.equals("expensiveuser")) {
						result.add(new ExpensiveSubscription(
								db.get("UserName"),
								db.get("Password"),
								db.get("FirstName"),
								db.get("LastName"),
								db.get("EmailAddress"),
								new SimpleDateFormat("E MMM d HH:mm:ss z yyyy", new Locale("en"))
								.parse((String) db.get("LastLogin"))
							));
					}
				}
			}
			
			db.close();
		} catch(FlatFileDatabaseException e) {
			e.printStackTrace();
		} catch(ParseException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}

		return result;
	}
}
