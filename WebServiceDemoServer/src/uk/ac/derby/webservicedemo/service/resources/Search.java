package uk.ac.derby.webservicedemo.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import uk.ac.derby.webservicedemo.service.ResourceSecure;
import uk.ac.derby.webservicedemo.service.log.Log;

@Path("search")
public class Search extends ResourceSecure {
	@GET
	@Produces("text/plain")
	public String search(
			@QueryParam("uid") String uid,
			@QueryParam("domain") String domain, 
			@QueryParam("problem") String problem,
			@QueryParam("search") String search) {
		Log.log("DO doSearch(" + uid + ", " + domain + ", " + problem + ", " + search + ")");
		return getSessionManager(uid).doSearch(domain, problem, search);
	}
}
