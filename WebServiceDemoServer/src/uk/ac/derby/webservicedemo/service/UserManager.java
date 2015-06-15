package uk.ac.derby.webservicedemo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.UUID;

class UserManager {
	
	private final static String accountFileName = Configuration.accountFileName;
	
	private SessionManagerInterface publicSessionManager = null;
	private HashMap<String, SessionManagerSecure> secureSessions = new HashMap<String, SessionManagerSecure>();
	private String path;
	
	UserManager(String path) {
		this.path = path;
		File directory = new File(path);
		if (!directory.exists()) {
			if (!directory.mkdirs())
				System.out.println("ERROR: Unable to create or access " + path);
		}
	}
	
	private String getUserFilePath() {
		return path + "/" + accountFileName;
	}
	
	/** Ensure necessary directories exist and the system is ready to run. */
	public boolean prepareSystem() {
		String fpath = getUserFilePath();
		File users = new File(fpath);
		try {
			BufferedReader input = new BufferedReader(new FileReader(users));
			String line;
			try {
				while ((line = input.readLine()) != null) {
					if (line.startsWith("#"))
						continue;
					String fileSecurityToken = line.split("\t")[0];
					File indexDir = new File(path + "/" + fileSecurityToken + "/index");
					if (indexDir.exists() && indexDir.isDirectory())
						System.out.println("Directory " + indexDir + " exists.");
					else {
						System.out.println("Directory and index " + indexDir + " created.");
					}
				}
				return true;
			} catch (IOException e) {
				System.out.println("Error reading user file: " + e);
				e.printStackTrace();
				return false;
			} catch (Exception ex) {
				System.out.println("Error checking user directory: " + ex);
				ex.printStackTrace();
				return false;
			} finally {
				try {
					input.close();
				} catch (IOException e) {
					System.out.println("Error closing user file: " + e);
					e.printStackTrace();
					return false;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File " + fpath + " not found.");
			return false;
		}		
	}
	
	/** Return approval from account file.  Return null if not found. */
	private Approval getApproval(String securityToken) {
		String fpath = getUserFilePath();
		File users = new File(fpath);
		try {
			BufferedReader input = new BufferedReader(new FileReader(users));
			String line;
			try {
				while ((line = input.readLine()) != null) {
					if (line.startsWith("#"))
						continue;
					String lineContents[] = line.split("\t");
					String fileSecurityToken = lineContents[0];
					if (fileSecurityToken.equals(securityToken)) {
						if (lineContents.length >= 3)
							return new Approval(fileSecurityToken, lineContents[1], lineContents[2]);
						else if (lineContents.length == 2)
							return new Approval(fileSecurityToken, lineContents[1]);
						else
							return new Approval(fileSecurityToken);
					}
				}
			} catch (IOException e) {
				System.out.println("Error reading user file: " + e);
				e.printStackTrace();
			} finally {
				try {
					input.close();
				} catch (IOException e) {
					System.out.println("Error closing user file: " + e);
					e.printStackTrace();
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File " + fpath + " not found.");
		}
		return null;		
	}
	
	String addUser(String purpose) {
		String userID = UUID.randomUUID().toString();
		PrintStream out = null;
		File newf;
		try {
			newf = File.createTempFile("tmp", ".txt");
			out = new PrintStream(new FileOutputStream(newf));
		} catch (IOException e) {
			System.out.println("Unable to create user due to: " + e);
			return null;
		}
		String fpath = getUserFilePath();
		File users = new File(fpath);
		try {
			try {
				BufferedReader input = new BufferedReader(new FileReader(users));
				String line;
				try {
					while ((line = input.readLine()) != null)
						out.println(line);
				} catch (IOException e) {
					System.out.println("Error reading user file: " + e);
					e.printStackTrace();
				} finally {
					try {
						input.close();
					} catch (IOException e) {
						System.out.println("Error closing user file: " + e);
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println("User file " + fpath + " not found.  It will be created.");
			}
			out.println(userID + "\t" + purpose);
		} finally {
			out.close();
		}
		if (!FileUtilities.copy(newf, users)) {
			System.out.println("ERROR: Unable to update users.");
			newf.delete();
			return null;
		}
		newf.delete();
		return userID;
	}
	
	boolean removeUser(String securityToken) {
		PrintStream out = null;
		File newf;
		try {
			newf = File.createTempFile("tmp", ".txt");
			out = new PrintStream(new FileOutputStream(newf));
		} catch (IOException e) {
			System.out.println("Unable to remove user due to: " + e);
			return false;
		}
		String fpath = getUserFilePath();
		File users = new File(fpath);
		boolean removed = false;
		try {
			try {
				BufferedReader input = new BufferedReader(new FileReader(users));
				String line;
				try {
					while ((line = input.readLine()) != null) {
						if (line.startsWith("#"))
							continue;
						String fileSecurityToken = line.split("\t")[0];
						if (!fileSecurityToken.equals(securityToken))
							out.println(line);
						else
							removed = true;
					}
				} catch (IOException e) {
					System.out.println("Error reading user file: " + e);
					e.printStackTrace();
				} finally {
					try {
						input.close();
					} catch (IOException e) {
						System.out.println("Error closing user file: " + e);
						e.printStackTrace();
					}
				}
			} catch (FileNotFoundException e) {
				System.out.println("User file " + fpath + " not found.  It will be created.");
			}
		} finally {
			out.close();
		}
		if (!FileUtilities.copy(newf, users)) {
			System.out.println("ERROR: Unable to update users.");
			newf.delete();
			return false;
		}
		newf.delete();
		if (!removed)
			System.out.println("ERROR: User " + securityToken + " not found.");
		return removed;
	}
	
	/** Get the SessionManager for public connections. */
	SessionManagerInterface getSessionManagerPublic() {
		if (publicSessionManager == null)
			publicSessionManager = new SessionManagerPublic();
		return publicSessionManager;
	}
	
	/** Get a SessionManager for a given secure connection. */
	SessionManagerInterface getSessionManagerSecure(String securityToken) {
		Approval validation = getApproval(securityToken);
		if (validation == null) {
			secureSessions.remove(securityToken);
			return getSessionManagerPublic();
		}
		SessionManagerSecure secureSessionManager = secureSessions.get(validation.getSecurityToken());
		if (secureSessionManager == null) {
			secureSessionManager = new SessionManagerSecure(path + "/" + securityToken, validation);
			secureSessions.put(validation.getSecurityToken(), secureSessionManager);
		}
		return secureSessionManager;
	}

	/** shut down all SessionManagerS. */
	void shutdown() {
		if (publicSessionManager != null)
			publicSessionManager.shutdown();
		for (SessionManagerSecure session: secureSessions.values())
			session.shutdown();
	}
	
}
