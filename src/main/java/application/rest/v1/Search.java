package application.rest.v1;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.util.Date;
import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.json.java.JSONArray;

import teetime.CoursePreferences;
import teetime.TeeTime;
import teetime.TimeSlot;
import teetime.TimeSlots;
import application.data.Reservation;

/**
 * Search for available tee times
 * 
 * POST method takes a JSON payload with login credentials and tee time to search for
 * 
 * @author djboulia
 *
 */
@Path("v1/teetime/search")
public class Search {

	public static JSONArray showTeeTimes(TeeTime session, Date theDate) {
		TimeSlots slots = session.getMatchingTeeTimes(theDate);
		
		System.err.println("Found Time Slots:");
		System.err.println(slots.toString());

		// put the results in a JSON array for return to the client
		JSONArray result = new JSONArray();
		Iterator<TimeSlot> it = slots.iterator();		
		
		while (it.hasNext()) {
			TimeSlot ts = it.next();
			result.add(ts.toString());
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
			
			String response = "{\"status\":\"Failed to log in!\"}";

			TeeTime session = new TeeTime("prestonwood.com");
			if (session.login(res.getUserid(), res.getPassword())) {
				
				System.out.println("Logged in as " + res.getUserid());
				
				try {
					Date theDate = res.getTeetime();
					CoursePreferences coursePrefs = res.getCourses();
					
					System.out.println("Searching for tee time at: " + theDate.toString());
					System.out.println("With course preferences: " + coursePrefs.toString());
					
					session.setCoursePreferences(coursePrefs);

					JSONArray result = showTeeTimes(session, theDate);
					response = result.serialize();
					
					System.out.println("returning JSON: " + response);
					
				} catch( Exception e ) {
					e.printStackTrace();
				}
				
				session.logout();					
			}

			return Response.ok(response).build();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return Response.serverError().build();
	}

}
