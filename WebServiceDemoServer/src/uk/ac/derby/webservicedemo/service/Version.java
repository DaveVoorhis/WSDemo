package uk.ac.derby.webservicedemo.service;

/*
 * 0.0.0
 *   - Initial release
 */

public class Version {

	private final static int majorVersion = 0;
	private final static int minorVersion = 0;
	private final static int release = 0;
	private final static String category = "Alpha";
	private final static String productName = "WebServiceDemo";
	
	public static String getProductName() {
		return productName;
	}
	
	public static String getVersionString() {
		return productName + " version " + majorVersion + "." + minorVersion + "." + release + " " + category;
	}
}
