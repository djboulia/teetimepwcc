package application.rest.v1;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import teetime.TeeTime;
import application.data.Member;

@Path("v1/member/validlogin")
public class ValidLogin {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(String body) {

		try {
			
			Member obj = Member.parse(body);
			if (obj == null) {
				System.err.println("Invalid JSON input" );
				return Response.serverError().build();
			}
			
			TeeTime session = new TeeTime("prestonwood.com");

			boolean result = session.login(obj.getUserid(), obj.getPassword());

			String response = "";

			if (result) {
				response = "{\"result\":true}";
			} else {
				response = "{\"result\":false}";
			}
			
			session.logout();
			
			return Response.ok(response).build();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return Response.serverError().build();
	}

}
