package uk.ac.derby.webservicedemo.service;

public class SessionManagerPublic implements SessionManagerInterface {

	private String nope() {
		return "ERROR: Invalid security token.";
	}

	@Override
	public String getVersion() {
		return Version.getVersionString();
	}
	
	@Override
	public String getSystemStatus() {
		return nope();
	}
	
	@Override
	public String doValidate(String domain, String problem, String plan, String options) {
		return nope();
	}

	@Override
	public String doSearch(String domain, String problem, String options) {
		return nope();
	}
	
	@Override
	public String getResults(String sessionID) {
		return nope();
	}

	@Override
	public String getFile(String sessionID, String fileName) {
		return nope();
	}

	@Override
	public String getFiles(String sessionID) {
		return nope();
	}

	@Override
	public void shutdown() {}

	@Override
	public String getActiveSessions() {
		return nope();
	}
}
