package uk.ac.derby.webservicedemo.service;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.glassfish.grizzly.http.server.HttpServer;

import uk.ac.derby.webservicedemo.service.log.Log;

import javax.ws.rs.core.UriBuilder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

public class Main {

	private static HttpServer httpServer = null;
	
	public static void run(String baseURI, int port) throws IOException {
		URI fullURI = UriBuilder.fromUri(baseURI).port(port).build();
		System.out.println("Starting HTTP server on " + baseURI + ":" + port);
		ResourceConfig rc = new ResourceConfig();
		
		rc.register(uk.ac.derby.webservicedemo.service.resources.Version.class);
		rc.register(uk.ac.derby.webservicedemo.service.resources.File.class);
		rc.register(uk.ac.derby.webservicedemo.service.resources.Files.class);
		rc.register(uk.ac.derby.webservicedemo.service.resources.Results.class);
		rc.register(uk.ac.derby.webservicedemo.service.resources.Search.class);
		rc.register(uk.ac.derby.webservicedemo.service.resources.Status.class);
		rc.register(uk.ac.derby.webservicedemo.service.resources.Validate.class);
			 
		httpServer = GrizzlyHttpServerFactory.createHttpServer(fullURI, rc);
		// Set up shutdown hook, to run termination cleanup
	    Runtime.getRuntime().addShutdownHook(new Terminator());
	    // Running...
		System.out.println(Version.getProductName() + " started.");
		System.out.println("WADL available at " + fullURI + "application.wadl");
		System.out.println("Try out " + fullURI + "version");
		System.out.println("Hit enter to stop it...");
		Log.syslog("Startup");
		try {
			System.in.read();
		} catch (IOException ioe) {}
		System.out.println("Shutdown requested...");
	}

	private static class Terminator extends Thread {
		public void run() {
			System.out.println("Shutting down HTTP server...");
			httpServer.shutdown();
			System.out.println("Shutting down service...");
			UserManagerSingleton.shutdown();
			System.out.println("Shutting down logger...");
			Log.close();
			System.out.println("System has shut down.");
		}
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		Options options = new Options();
		OptionGroup help = new OptionGroup();
		help.addOption(OptionBuilder.withLongOpt("help").withDescription("Display this help information.").create());
		options.addOptionGroup(help);
		OptionGroup addUser = new OptionGroup();
		addUser.addOption(OptionBuilder.withLongOpt("adduser").withDescription("Add a user.").hasArg().withArgName("description").create());
		options.addOptionGroup(addUser);
		OptionGroup delUser = new OptionGroup();
		delUser.addOption(OptionBuilder.withLongOpt("deluser").withDescription("Remove a user").hasArg().withArgName("userID").create());
		options.addOptionGroup(delUser);
		OptionGroup server = new OptionGroup();
		server.addOption(OptionBuilder.withLongOpt("host").withDescription("host").hasArg().withArgName("host").create());
		server.addOption(OptionBuilder.withLongOpt("port").withDescription("port").hasArg().withArgName("port").create());
		options.addOptionGroup(server);
		CommandLineParser parser = new PosixParser();
		try {
		    CommandLine line = parser.parse(options, args);
		    if (line.hasOption("help")) {
				(new HelpFormatter()).printHelp("demo", options);
				return;
			} else if (line.hasOption("adduser")) {
		    	String purpose = line.getOptionValue("adduser");
				String user = uk.ac.derby.webservicedemo.service.UserManagerSingleton.AddUser(purpose);
				if (user != null)
					System.out.println("OK: Added user " + user + " for " + purpose);		    	
		    } else if (line.hasOption("deluser")) {
		    	String user = line.getOptionValue("deluser");
				boolean succeeded = uk.ac.derby.webservicedemo.service.UserManagerSingleton.RemoveUser(user);
				if (succeeded)
					System.out.println("OK: Removed user " + user);
		    } else {
				InetAddress hostAddress = null;
				String baseURI = Configuration.baseURI;
				try {
					hostAddress = InetAddress.getLocalHost();
					baseURI = "http://" + hostAddress.getCanonicalHostName() + "/";
				} catch (UnknownHostException e1) {
					System.out.println("Host not recognised.");
					return;
				}
				int port = Configuration.defaultPort;
				if (!UserManagerSingleton.prepareSystem()) {
					System.out.println("Unable to initialise system.  You need to add at least one user so that the users file gets created.");
					return;
				}
			    if (line.hasOption("host")) {
			        baseURI = "http://" + line.getOptionValue("host") + "/";
			    }
			    if (line.hasOption("port")) {
			    	try {
			    		port = Integer.parseInt(line.getOptionValue("port"));
			    	} catch (NumberFormatException nfe) {
			    		System.out.println("Port must be numeric.");
			    		return;
			    	}
			    	if (port < 0 || port > 65535) {
			    		System.out.println("Port number must be between 0 and 65535.");
			    		return;
			    	}
			    }
				run(baseURI, port);
		    }
		}
		catch (ParseException exp) {
			(new HelpFormatter()).printHelp("demo", options);
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block this is for the running
			e.printStackTrace();
		}
	}

}
