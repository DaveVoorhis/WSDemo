package uk.ac.derby.webservicedemo.service.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Logger {
	
	private FileWriter writer = null;
	private String fileName = null;

	private void init() throws IOException {
		if (writer == null) {
			writer = new FileWriter(new File(fileName), true);
		}
	}
	
	public Logger(String fileName) throws IOException {
		this.fileName = fileName;
		init();
	}
	
	public void close() {
		if (writer != null)
			try {
				writer.close();
			} catch (IOException e) {
				System.out.println("Problem closing Logger: " + e);
			}
		writer = null;
	}
	
	// TODO - use of synchronized here forces lock-step sync around Log.  Bad!  Use a non-blocking queue and a receiver thread.
	public synchronized void log(String msg) {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		try {
			if (writer == null) {
				System.out.println("Log " + fileName + " is inactive.  Message is: " + msg);
			} else {
				writer.write(timeStamp + " - " + msg + "\n");
				writer.flush();
			}
		} catch (Exception ioe) {
			System.out.println("ERROR: Unable to write to log " + fileName + " due to " + ioe);
			System.out.println("Unable to log message: " + msg);
		}
	}

}
