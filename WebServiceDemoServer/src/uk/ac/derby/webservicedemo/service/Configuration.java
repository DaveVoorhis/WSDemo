package uk.ac.derby.webservicedemo.service;

public class Configuration {
	
	public static String enginePath = "var";

	public static String syslogFileName = "log/service.syslog.log";

	public static String activitylogFileName = "log/service.activity.log";

	public static String accountFileName = "users";

	public static int defaultPort = 8080;

	public static String baseURI = "http://localhost/";

	public static int inactivityMinutesBeforeSessionDeactivated = 1;
}
