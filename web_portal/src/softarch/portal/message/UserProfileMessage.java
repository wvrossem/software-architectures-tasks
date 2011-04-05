package softarch.portal.message;

import softarch.portal.data.UserProfile;

public class UserProfileMessage extends Message {
	
	public enum Method {
		INSERT,
		UPDATE,
		DELETE
	}
	
	UserProfile userProfile;
	Method method;

	UserProfileMessage(UserProfile userProfile, Method method) {
		this.userProfile = userProfile;
		this.method      = method;
		
	}
	
	@Override
	public String asSql() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String asFlatfile() {
		// TODO Auto-generated method stub
		return null;
	}

}
