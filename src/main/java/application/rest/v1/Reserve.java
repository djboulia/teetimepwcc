package application.rest.v1;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import teetime.TeeTime;
import application.data.Reservation;

@Path("v1/teetime/reserve")
public class Reserve {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(String body) {

		try {
			
			Reservation res = Reservation.parse(body);
			if (res == null) {
				System.err.println("Invalid JSON input" );
				return Response.serverError().build();
			}
			
			Date theDate = res.getTeetime();
			TeeTime session = new TeeTime("prestonwood.com");
			session.setCoursePreferences(res.getCourses());

			boolean result = session.login(res.getUserid(), res.getPassword());

			String response = "";

			if (result) {
				if (session.reserve(theDate, res.getGolfers()) != null) {
					response = "{\"status\":\"Success!\"}";
				} else {
					response = "{\"status\":\"Login successful but reservation failed!\"}";
				}
			} else {
				response = "{\"status\":\"Failed to log in!\"}";
			}
			
			session.logout();
			
			return Response.ok(response).build();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return Response.serverError().build();
	}

}
