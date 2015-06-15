package uk.ac.derby.webservicedemo.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import uk.ac.derby.webservicedemo.service.ResourceSecure;
import uk.ac.derby.webservicedemo.service.log.Log;

@Path("results")
public class Results extends ResourceSecure {
	@GET
	@Produces("text/plain")
	public String search(
			@QueryParam("uid") String uid,
			@QueryParam("session") String sessionID) {
		Log.log("DO getResults(" + uid + ", " + sessionID + ")");
		return getSessionManager(uid).getResults(sessionID);
	}
}
