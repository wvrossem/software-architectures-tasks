package softarch.portal.db.test;

import java.util.Date;

import softarch.portal.data.ExpensiveSubscription;
import softarch.portal.data.UserProfile;
import softarch.portal.db.flatfile.UserDatabase;


public class FlatFileTest {

	public static void main(String[] args) {
		try {
			
			UserDatabase db = new UserDatabase();
			
			ExpensiveSubscription user = new ExpensiveSubscription(
					"wvrossem", "wvrossem", "Wouter", "Van Rossem", "wvrossem@vub.ac.be", new Date());
			
			db.insert(user);
			
			UserProfile profile = db.find("wvrossem");
			
			System.out.println(profile.getLastName());
			System.out.println(profile.getLastLogin());
			
			System.out.println(db.exists("wvrossem"));
			
			user = new ExpensiveSubscription(
					"wvrossem", "wvrossem", "Wouter", "Van Rossem", "Wouter.Van.Rossem@vub.ac.be", new Date());
			
			db.update(user);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
