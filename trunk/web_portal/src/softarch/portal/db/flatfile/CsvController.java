package softarch.portal.db.flatfile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;


import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class CsvController {
	
	private boolean databaseExisits(String database) {
		return new File(database+".csv").exists();
	}
	
	public void createDatabase(String database, String[] fieldnames) throws FlatFileDatabaseException {
		try {
			if ( databaseExisits(database) ) throw new FlatFileDatabaseException("Database already exists");
			
			CsvWriter csvOutput = new CsvWriter(new FileWriter(database+".csv", true), ',');
			
			for (String fieldname : fieldnames) {
				csvOutput.write(fieldname);
			}
			
			csvOutput.endRecord();
			csvOutput.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insert(String database, CsvValues csvValues) throws FlatFileDatabaseException {
		try {
			if ( !databaseExisits(database) ) throw new FlatFileDatabaseException("Database does not exist");
			
			if ( exists(database, "UserName", (String)csvValues.get(0)) ) {
				throw new FlatFileDatabaseException("Record already exists.");
			} else {

				CsvWriter csvOutput = new CsvWriter(new FileWriter(database+".csv", true), ',');
				
				// csvOutput.write(csvValues.toString());	
				
				for ( Object el : csvValues.getValues() ) {
					csvOutput.write(el.toString());
				}
				
				csvOutput.endRecord();
				csvOutput.close();
			} 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update(String database, CsvValues csvValues) throws FlatFileDatabaseException {
		if ( !databaseExisits(database) ) throw new FlatFileDatabaseException("Database does not exist");
		
		if ( exists(database, "UserName", (String)csvValues.get(0)) ) {
			// TODO implement update
			throw new FlatFileDatabaseException("Record exists.");
		} else {
			throw new FlatFileDatabaseException("Record does not exist");
		} 
	}
	
	public CsvValues find(String database, String fieldName, String value) {
		try {
			if ( !databaseExisits(database) ) throw new FlatFileDatabaseException("Database does not exist");
			
			CsvReader db = new CsvReader(database+".csv");
			
			db.readHeaders();
			
			int headerCount = db.getHeaderCount();
			
			String[] csvValues = new String [headerCount];
			
			while (db.readRecord()) {
				if (db.get(fieldName).equals(value)) {
					for (int i = 0; i<headerCount; i++) {
						csvValues[i] = db.get(i);
					}
					return new CsvValues(csvValues);
				} 
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FlatFileDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public boolean exists(String database, String fieldName, String value) {
		try {
			if ( !databaseExisits(database) ) throw new FlatFileDatabaseException("Database does not exist");
			
			CsvReader db = new CsvReader(database+".csv");
			
			db.readHeaders();
			
			while (db.readRecord()) {
				if (db.get(fieldName).equals(value)) {
					return true;
				}
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FlatFileDatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
}
