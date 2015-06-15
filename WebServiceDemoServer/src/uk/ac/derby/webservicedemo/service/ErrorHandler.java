package uk.ac.derby.webservicedemo.service;

public class ErrorHandler {

	public static void processError(Throwable t, String additionalInformation) {
		// TODO - implement error logging and notification here
		System.out.println("Service error: " + t + ": " + additionalInformation);
	}
	
	public static void processError(String information) {
		// TODO - implement error logging and notification here
		System.out.println("Service error: " + information);
	}
	
}
