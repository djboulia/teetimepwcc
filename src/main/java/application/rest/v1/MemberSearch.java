package application.rest.v1;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import java.util.Iterator;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

import teetime.Golfer;
import teetime.Golfers;
import teetime.TeeTime;
import application.data.MemberNameSearch;

@Path("v1/member/search")
public class MemberSearch {
	
	public static JSONArray JsonGolfers(Golfers golfers) {
		
		// put the results in a JSON array for return to the client
		JSONArray result = new JSONArray();

		if ( golfers != null && golfers.size() > 0) {
			System.out.println("Found members:");
			System.out.println(golfers.toString());
			
			Iterator<Golfer> it = golfers.iterator();		
			
			while (it.hasNext()) {
				Golfer golfer = it.next();
				
				JSONObject obj = new JSONObject();
				obj.put("name", golfer.getName());
				obj.put("id", golfer.getId());
				
				result.add(obj);
			}
			
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
			
			MemberNameSearch obj = MemberNameSearch.parse(body);
			if (obj == null) {
				System.err.println("Invalid JSON input" );
				return Response.serverError().build();
			}
			
			TeeTime session = new TeeTime("prestonwood.com");

			String response = "";

			if (session.login(obj.getUserid(), obj.getPassword())) {
				System.out.println("Logged in as " + obj.getUserid());
				
				try {
					Golfers golfers = session.getMemberList(obj.getLastName());

					JSONArray result = JsonGolfers(golfers);
					response = result.serialize();
					
					System.out.println("returning JSON: " + response);
					
				} catch( Exception e ) {
					e.printStackTrace();
					
					response = "{\"status\":\"Error: exception\"}";
				}
				
				session.logout();					
			} else {
				response = "{\"status\":\"Error: login failed!\"}";
			}
			
			return Response.ok(response).build();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return Response.serverError().build();
	}

}
