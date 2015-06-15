package uk.ac.derby.webservicedemo.service;

public interface SessionManagerInterface {
	/** Shut down this SessionManager. */
	public void shutdown();

	/** Get system status. 
	 * 
	 * @return - String containing summary of system state, or exception information if connection failed.
	 */
	public String getSystemStatus();

	/** Get system version. 
	 * 
	 * @return - String containing system version, or exception information if connection failed.
	 */
	public String getVersion();

	/** Validate "continuous effects, events and processes."
	 * 
	 * @param domain - domain file contents
	 * @param problem - problem file contents
	 * @param plan - plan file contents
	 * @param options - options string, per 'validate' program
	 * @return Session ID
	 */
	public String doValidate(String domain, String problem, String plan, String options);

	/** Launch a downward search for a solution.
	 * 
	 * @param domain - domain file contents
	 * @param problem - problem file contents
	 * @param search - SEARCH_OPTION string, per the 'plan' program.  E.g., lazy_greedy(ff())
	 * @return Session ID
	 */
	public String doSearch(String domain, String problem, String search);

	/** Obtain results of doValidate() or doSearch().  Each line is preceded with \t except the last, which
	 * is preceded with 'x'.  Return 'w' if results aren't ready; come back later and try again.
	 * 
	 * @param sessionID
	 * @return - result string.
	 */
	public String getResults(String sessionID);	
	
	/** Get file contents. 
	 * 
	 * @param sessionID
	 * @param fileName - name of file
	 * @return - file contents
	 */
	public String getFile(String sessionID, String fileName);
	
	/** Get list of files.
	 * 
	 * @param sessionID
	 * @return - \n separated list of file names
	 */
	public String getFiles(String sessionID);
	
	
	/** Get Active Sessions. 
	 * 
	 * @return - String containing the number of active sessions.
	 */
	public String getActiveSessions();
}
