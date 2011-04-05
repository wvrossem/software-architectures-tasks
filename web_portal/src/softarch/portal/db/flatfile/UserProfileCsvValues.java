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
	
	UserProfileCsvValues(Object[] values) throws DatabaseException {
		super(values);
		
		for (int i = 0; i<values.length; i++) {
			if (!values[i].getClass().equals(types[i]) ) {
				throw new DatabaseException("Type mismatch");
			}
		}
	}
	
	UserProfileCsvValues(UserProfile profile) {
		Object[] values = new Object[6];
		
		values[1] = profile.getUsername();
		values[2] = profile.getPassword();
		values[3] = profile.getFirstName();
		values[4] = profile.getLastName();
		values[5] = profile.getEmailAddress();
		values[6] = profile.getLastLogin();
		
		this.values = values;
	}

}
