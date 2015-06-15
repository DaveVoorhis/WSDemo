package uk.ac.derby.webservicedemo.client;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

public class DemoClient implements DemoClientInterface {
	
	private Client client;
	private WebResource resource;
	
	public DemoClient(String url) {
		client = new Client();
		client.setFollowRedirects(true);
		resource = client.resource(url);
	}
	
	@Override
	public String getVersion() throws UniformInterfaceException, ClientHandlerException {
	    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
	    String command = "version";
	    return resource.path(command).queryParams(params).get(String.class);
	 }

	@Override
	public String getSystemStatus(String uid) throws UniformInterfaceException, ClientHandlerException {
	    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
	    params.add("uid", uid);
	    String command = "status";
	    return resource.path(command).queryParams(params).get(String.class);
	}
	
	@Override
	public String doValidate(String uid, String domain, String problem, String plan, String options) throws UniformInterfaceException, ClientHandlerException {
	    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
	    params.add("uid", uid);
	    params.add("domain", domain);
	    params.add("problem", problem);
	    params.add("plan", plan);
	    params.add("options", options);
	    String command = "validate";
	    return resource.path(command).queryParams(params).get(String.class);	
	}

	@Override
	public String doSearch(String uid, String domain, String problem, String search) throws UniformInterfaceException, ClientHandlerException {
	    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
	    params.add("uid", uid);
	    params.add("domain", domain);
	    params.add("problem", problem);
	    params.add("search", search);
	    String command = "search";
	    return resource.path(command).queryParams(params).get(String.class);	
	}

	@Override
	public String getResults(String uid, String sessionID) throws UniformInterfaceException, ClientHandlerException {
	    MultivaluedMap<String, String> params = new MultivaluedMapImpl();
	    params.add("uid", uid);
	    params.add("session", sessionID);
	    String command = "results";
	    return resource.path(command).queryParams(params).get(String.class);
	}
	
	@Override
	public String getFile(String uid, String sessionID, String fileName) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
	    params.add("uid", uid);
	    params.add("sessionid", sessionID);
	    params.add("file", fileName);
	    String command = "file";
	    return resource.path(command).queryParams(params).get(String.class);
	}

	@Override
	public String getFiles(String uid, String sessionID) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
	    params.add("uid", uid);
	    params.add("sessionid", sessionID);
	    String command = "files";
	    return resource.path(command).queryParams(params).get(String.class);
	}

	@Override
	public String getActiveSessions(String uid) {
		MultivaluedMap<String, String> params = new MultivaluedMapImpl();
	    params.add("uid", uid);
	    String command = "activeSessions";
	    return resource.path(command).queryParams(params).get(String.class);
	}
	
}
