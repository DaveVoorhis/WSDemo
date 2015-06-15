package uk.ac.derby.webservicedemo.service;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

public class SessionManagerSecure implements SessionManagerInterface {
	
	private final int inactivityMinutesBeforeSessionDeactivated = Configuration.inactivityMinutesBeforeSessionDeactivated;
	
	private HashMap<String, Session> sessions = new HashMap<String, Session>();
	private boolean cleanerShouldRun = true;
	private boolean cleanerIsRunning = false;
	private Thread cleaner;
	
	private synchronized void clean() {
		
		for(Iterator<HashMap.Entry<String, Session>> it = sessions.entrySet().iterator(); it.hasNext(); ) 
		{
			HashMap.Entry<String, Session> sessionEntry = it.next();
		    if(sessionEntry.getValue().getSecondsSinceLastActivity() / 60 > inactivityMinutesBeforeSessionDeactivated) 
		    {
		    	sessionEntry.getValue().close();
		    	it.remove();
		    }
	    }
	}
	
	private void cleaner() {
		cleanerIsRunning = true;
		while (cleanerShouldRun) {
			clean();
			try {
				Thread.sleep(1000 * 30);
			} catch (InterruptedException e) {
				cleanerShouldRun = false;
			}
		}
		cleanerIsRunning = false;
		cleaner = null;
	}
	
	public SessionManagerSecure(String directory, Approval validation) {
		File dir = new File(directory);
		if (!dir.exists()) {
			if (!dir.mkdirs())
				System.out.println("ERROR: Unable to create or access " + dir);
		}
		cleaner = new Thread() {
			public void run() {
				cleaner();
			}
		};
		cleaner.start();
	}
	
	public void shutdown() {
		cleanerShouldRun = false;
		try {cleaner.interrupt();} catch (java.lang.NullPointerException ignore) {}
		while (cleanerIsRunning) {
			try {Thread.sleep(1000);} catch (InterruptedException e) {}
		}
		for (Session session : sessions.values())
			session.close();
	}
	
	/* (non-Javadoc)
	 * @see uk.ac.derby.semantics.session.SessionManagerInterface#getVersion()
	 */
	public String getVersion() {
		return Version.getVersionString();
	}

	/* (non-Javadoc)
	 * @see uk.ac.derby.semantics.session.SessionManagerInterface#getSystemStatus()
	 */
	public String getSystemStatus() {
		return "System is running";
	}
	
	@Override
	public synchronized String doValidate(String domain, String problem, String plan, String options) {
		String sessionID = UUID.randomUUID().toString();
		Session session = new Session(sessionID);
		sessions.put(sessionID, session);
		session.doValidate(domain, problem, plan, options);
		return sessionID;
	}

	@Override
	public synchronized String doSearch(String domain, String problem, String searchSpecification) {
		String sessionID = UUID.randomUUID().toString();
		Session session = new Session(sessionID);
		sessions.put(sessionID, session);
		session.doSearch(domain, problem, searchSpecification);		
		return sessionID;
	}

	@Override
	public synchronized String getResults(String sessionID) {
		Session session = sessions.get(sessionID);
		if (session == null)
			return "xError: invalid session ID";
		else {	
			return session.getResults();
		}
	}

	@Override
	public synchronized String getFile(String sessionID, String fileName) {
		Session session = sessions.get(sessionID);
		if (session == null)
			return "xError: invalid session ID";
		else {
			return session.fileToString(fileName);
		}
	}

	@Override
	public synchronized String getFiles(String sessionID) {
		Session session = sessions.get(sessionID);
		if (session == null)
			return "xError: invalid session ID";
		else {
			return session.directoryContentsToString();
		}
	}

	@Override
	public String getActiveSessions() {
		return Integer.toString(sessions.size());
	}

}
