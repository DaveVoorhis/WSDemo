package uk.ac.derby.webservicedemo.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import uk.ac.derby.webservicedemo.service.ResourceSecure;
import uk.ac.derby.webservicedemo.service.log.Log;

@Path("status")
public class Status extends ResourceSecure {
	@GET
	@Produces("text/plain")
	public String search(
			@QueryParam("uid") String uid) {
		Log.log("DO getResults(" + uid + ")");
		return getSessionManager(uid).getSystemStatus();
	}
}
