package softarch.portal.db.flatfile;

import java.util.Date;

import softarch.portal.data.UserProfile;

public class UserProfileCsvValues extends CsvValues {
	
	Object types[] = {
		String.class,
		String.class,
		String.class,
		String.class,
		String.class,
		Date.class
	};
	
	UserProfileCsvValues(Object[] values) throws FlatFileDatabaseException {
		super(values);
		
		for (int i = 0; i<values.length; i++) {
			if (!values[i].getClass().equals(types[i]) ) {
				throw new FlatFileDatabaseException("Type mismatch");
			}
		}
	}
	
	UserProfileCsvValues(UserProfile profile) {
		Object[] values = new Object[6];
		
		values[0] = profile.getUsername();
		values[1] = profile.getPassword();
		values[2] = profile.getFirstName();
		values[3] = profile.getLastName();
		values[4] = profile.getEmailAddress();
		values[5] = profile.getLastLogin();
		
		this.values = values;
	}

}
