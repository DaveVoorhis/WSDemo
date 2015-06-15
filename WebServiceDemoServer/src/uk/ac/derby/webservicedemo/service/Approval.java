package uk.ac.derby.webservicedemo.service;

public class Approval {
	private String securityToken;
	private String id;
	private String options;
	
	Approval(String securityToken, String id, String options) {
		this.securityToken = securityToken;
		this.id = id;
		this.options = options;
	}
	
	Approval(String securityToken, String id) {
		this(securityToken, id, null);
	}
	
	Approval(String securityToken) {
		this(securityToken, null);
	}

	public Approval() {
		this(null);
	}
	
	String getSecurityToken() {
		return securityToken;
	}
	
	String getID() {
		return id;
	}
	
	String getOptions() {
		return options;
	}
}
