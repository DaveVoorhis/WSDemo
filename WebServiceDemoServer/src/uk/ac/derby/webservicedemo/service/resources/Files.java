package uk.ac.derby.webservicedemo.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import uk.ac.derby.webservicedemo.service.ResourceSecure;
import uk.ac.derby.webservicedemo.service.log.Log;

@Path("files")
public class Files extends ResourceSecure {
	@GET
	@Produces("text/plain")
	public String file(
			@QueryParam("uid") String uid,
			@QueryParam("sessionid") String sessionid) {
		Log.log("DO getFile(" + uid + ")");
		return getSessionManager(uid).getFiles(sessionid);
	}
}
