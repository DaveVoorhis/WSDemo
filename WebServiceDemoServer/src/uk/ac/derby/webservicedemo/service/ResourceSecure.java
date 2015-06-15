package uk.ac.derby.webservicedemo.service;

public class ResourceSecure {
	protected SessionManagerInterface getSessionManager(String securityToken) {
		return UserManagerSingleton.getSessionManagerSecure(securityToken);
	}
}
