package application.rest.v1;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.json.java.JSONObject;

import teetime.TeeTime;
import teetime.CoursePreferences;
import teetime.TimeSlot;
import application.data.Reservation;

@Path("v1/teetime/reserve")
public class Reserve {

	public static JSONObject JsonTimeSlot(TimeSlot ts) {
		
		// put the results in a JSON array for return to the client
		JSONObject result = new JSONObject();

		if ( ts != null ) {
			System.out.println("Found time slot:");
			System.out.println(ts.toString());
			
			result.put("time", ts.getTime().toString());
			result.put("course", CoursePreferences.toString(ts.getCourse()));
		} else {
			System.out.println("No members found!");
		}

		return result;
	}
	
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
				
				TimeSlot ts = session.reserve(theDate, res.getGolfers());
				if (ts != null) {
					JSONObject jsonResult = JsonTimeSlot(ts);
					response = jsonResult.serialize();
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
