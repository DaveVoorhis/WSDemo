package uk.ac.derby.webservicedemo.service.log;

import java.io.IOException;

import uk.ac.derby.webservicedemo.service.Configuration;

public class Log {

	// TODO - set via command line args
	private static final String syslogFileName = Configuration.syslogFileName;
	
	// TODO - set via command line args
	private static final String activitylogFileName = Configuration.activitylogFileName;

	private static Logger sysLog = null;
	private static Logger activityLog = null;
	
	private static void err(IOException e, String fname, String msg) {
		System.out.println("ERROR: Unable to open " + syslogFileName + " due to: " + e);
		System.out.println("Unrecorded syslog message: " + msg);		
	}
	
	public static void syslog(String msg) {
		if (sysLog == null)
			try {
				sysLog = new Logger(syslogFileName);
			} catch (IOException e) {
				err(e, syslogFileName, msg);
				return;
			}
		sysLog.log(msg);		
	}

	public static void log(String msg) {
		if (activityLog == null)
			try {
				activityLog = new Logger(activitylogFileName);
			} catch (IOException e) {
				err(e, activitylogFileName, msg);
				return;
			}
		activityLog.log(msg);
	}

	public static void close() {
		if (sysLog != null)
			sysLog.close();
		if (activityLog != null)
			activityLog.close();
	}
	
}
