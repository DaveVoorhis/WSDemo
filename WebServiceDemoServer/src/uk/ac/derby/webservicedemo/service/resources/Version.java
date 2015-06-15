package uk.ac.derby.webservicedemo.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import uk.ac.derby.webservicedemo.service.ResourcePublic;

// The Java class will be hosted at the URI path "/version"
@Path("version")
public class Version extends ResourcePublic {
	// The Java method will process HTTP GET requests
	@GET 
	// The Java method will produce content identified by the MIME Media type "text/plain"
	@Produces("text/plain")
	public String version() {
		return getSessionManager().getVersion();
	}
}
