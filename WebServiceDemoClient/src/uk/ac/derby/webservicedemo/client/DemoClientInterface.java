package uk.ac.derby.webservicedemo.client;

public interface DemoClientInterface {

	/** Get system version. 
	 * 
	 * @return - String containing system version, or exception information if connection failed.
	 */
	public String getVersion();

	/** Get system status. 
	 * 
	 * @param uid - user id
	 * @return - String containing summary of system state, or exception information if connection failed.
	 */
	public String getSystemStatus(String uid);

	/** Validate "continuous effects, events and processes."
	 * 
	 * @param uid - user id
	 * @param domain - domain file contents
	 * @param problem - problem file contents
	 * @param plan - plan file contents
	 * @param options - options string, per 'validate' program
	 * @return Session ID
	 */
	public String doValidate(String uid, String domain, String problem, String plan, String options);

	/** Launch a downward search for a solution.
	 * 
	 * @param uid - user id
	 * @param domain - domain file contents
	 * @param problem - problem file contents
	 * @param options - SEARCH_OPTION string, per the 'plan' program.  E.g., lazy_greedy(ff())
	 * @return Session ID
	 */
	public String doSearch(String uid, String domain, String problem, String options);

	/** Obtain results of doValidate() or doSearch().  Each line is preceded with \t except the last, which
	 * is preceded with 'x'.  Return 'w' if results aren't ready; come back later and try again.
	 * 
	 * @param uid - user id
	 * @param sessionID
	 * @return - result string.
	 */
	public String getResults(String uid, String sessionID);	
	
	/** Get file contents. 
	 * 
	 * @param uid - user id
	 * @param sessionID
	 * @param fileName - name of file
	 * @return - file contents
	 */
	public String getFile(String uid, String sessionID, String fileName);
	
	/** Get list of files.
	 * 
	 * @param uid - user id
	 * @param sessionID
	 * @return - \n separated list of file names
	 */
	public String getFiles(String uid, String sessionID);
	
	/** Get Active Sessions. 
	 * 
	 * @param uid - user id
	 * @return - String containing summary of the number of active sessions.
	 */
	public String getActiveSessions(String uid);
}
