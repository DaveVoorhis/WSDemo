package uk.ac.derby.webservicedemo.service;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.nio.file.attribute.BasicFileAttributes;

import uk.ac.derby.webservicedemo.service.log.Log;

class Session {

	private String sessionID;
	private Date lastActivity;
	private Path workspace;
	private ConcurrentLinkedQueue<String> output;
	
	private void updateLastActivity() {
		lastActivity = new Date();
	}
	
	Session(String sessionID) {
		this.sessionID = sessionID;
		output = new ConcurrentLinkedQueue<String>();
		updateLastActivity();
		try {
			workspace = Files.createTempDirectory(Paths.get("var"), "tmp");
		} catch (IOException e) {
			Log.log("Unable to create temporary directory: " + e);
			workspace = null;
		}
	}

	String getSessionID() {
		return sessionID;
	}
	
	ConcurrentLinkedQueue<String> getOutputQueue() {
		return output;
	}
	
	long getSecondsSinceLastActivity() {
		return ((new Date()).getTime() - lastActivity.getTime()) / 1000L;
	}
	
	private void purge(Path path) {
		try {
		  Files.walkFileTree(path, new SimpleFileVisitor<Path>() {				 
		      @Override
		      public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {		 
		          Log.log("Deleting file: " + file);
		          Files.delete(file);
		          return FileVisitResult.CONTINUE;
		      }
		      @Override
		      public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		          Log.log("Deleting directory: " + dir);
		          if (exc == null) {
		              Files.delete(dir);
		              return FileVisitResult.CONTINUE;
		          } else {
		              throw exc;
		          }
		      }
		  });
		} catch (IOException e) {
		  e.printStackTrace();
		}
	}
	
	void close() {
		if (workspace != null) {
			purge(workspace);
		}
	}

	/*
	 * The following methods implement the session's functionality.  The methods below
	 * are stripped-down versions of those used in a project.
	 */
	
	public void doValidate(String domain, String problem, String plan, String options) {
		System.out.println("doValidate was invoked.");
	}

	public void doSearch(String domain, String problem, String searchSpecification) {
		System.out.println("doSearch was invoked.");
	}

	public String getResults() {
		return "These are the results.";
	}

	public String fileToString(String fileName) {
		return "In the original project, this would be the contents of fileName.";
	}

	public String directoryContentsToString() {
		return "In the original project, this would be the contents of the session's directory.";
	}

}
