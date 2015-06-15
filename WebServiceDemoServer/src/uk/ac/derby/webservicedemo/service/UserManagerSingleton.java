package uk.ac.derby.webservicedemo.service;

public class UserManagerSingleton {

	private final static String searchEnginePath = Configuration.enginePath;
	
	private static UserManager theUserManager;
	
	private static UserManager getInstance() {
		if (theUserManager == null)
			theUserManager = new UserManager(searchEnginePath);
		return theUserManager;
	}
	
	public static String AddUser(String purpose) {
		return getInstance().addUser(purpose);
	}
	
	public static boolean RemoveUser(String securityToken) {
		return getInstance().removeUser(securityToken);
	}
	
	static SessionManagerInterface getSessionManagerPublic() {
		return getInstance().getSessionManagerPublic();
	}
	
	static SessionManagerInterface getSessionManagerSecure(String securityToken) {
		return getInstance().getSessionManagerSecure(securityToken);
	}
	
	static void shutdown() {
		getInstance().shutdown();
	}

	public static boolean prepareSystem() {
		return getInstance().prepareSystem();
	}

}
