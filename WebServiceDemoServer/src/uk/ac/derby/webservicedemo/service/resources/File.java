package uk.ac.derby.webservicedemo.service.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import uk.ac.derby.webservicedemo.service.ResourceSecure;
import uk.ac.derby.webservicedemo.service.log.Log;

@Path("file")
public class File extends ResourceSecure {
	@GET
	@Produces("text/plain")
	public String file(
			@QueryParam("uid") String uid,
			@QueryParam("sessionid") String sessionid,
			@QueryParam("file") String fileName) {
		Log.log("DO getFile(" + uid + ", " + fileName + ")");
		return getSessionManager(uid).getFile(sessionid, fileName);
	}
}
