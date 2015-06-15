package uk.ac.derby.webservicedemo.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import uk.ac.derby.webservicedemo.service.ResourceSecure;
import uk.ac.derby.webservicedemo.service.log.Log;

@Path("validate")
public class Validate extends ResourceSecure {
	@GET
	@Produces("text/plain")
	public String search(
			@QueryParam("uid") String uid,
			@QueryParam("domain") String domain, 
			@QueryParam("problem") String problem,
			@QueryParam("plan") String plan,
			@QueryParam("options") String options) {
		Log.log("DO doSearch(" + uid + ", " + domain + ", " + problem + ", " + plan + ", " + options + ")");
		return getSessionManager(uid).doValidate(domain, problem, plan, options);
	}
}
