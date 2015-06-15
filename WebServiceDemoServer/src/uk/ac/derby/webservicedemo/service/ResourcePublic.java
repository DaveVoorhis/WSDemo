package uk.ac.derby.webservicedemo.service;

public class ResourcePublic {
	protected SessionManagerInterface getSessionManager() {
		return UserManagerSingleton.getSessionManagerPublic();
	}
}
