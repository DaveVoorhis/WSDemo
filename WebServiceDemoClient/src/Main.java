import uk.ac.derby.webservicedemo.client.DemoClient;

class Main {
	public static void main(String[] args) {	
		
		// serverAddress must match the one the server is listening on
		String serverAddress = "http://10.189.7.177:8080";

		// see server's /var/users file
		String userToken = "9cf0794a-698f-42a5-b7c4-6414fb100020";
		
		// Connect to server
		DemoClient demo = new DemoClient(serverAddress);
		
		// Invoke various server-side operations.
		System.out.println("Version: " + demo.getVersion());
		System.out.println("doSearch: " + demo.doSearch(userToken, "blah", "blah", "blah"));
		System.out.println("doValidate: " + demo.doValidate(userToken, "zap", "zot", "ziff", "zip"));
	}
}
