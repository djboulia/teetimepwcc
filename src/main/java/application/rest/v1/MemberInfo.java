package application.rest.v1;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ibm.json.java.JSONObject;

import teetime.Golfer;
import teetime.TeeTime;
import application.data.Member;

@Path("v1/member/info")
public class MemberInfo {
	
	public static JSONObject JsonGolfer(Golfer golfer) {
		
		// put the results in a JSON array for return to the client
		JSONObject result = new JSONObject();

		if ( golfer != null) {
			System.out.println("Found golfer:");
			System.out.println(golfer.toString());
			
			result.put("name", golfer.getName());
			result.put("id", golfer.getId());
				
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
			
			Member obj = Member.parse(body);
			if (obj == null) {
				System.err.println("Invalid JSON input" );
				return Response.serverError().build();
			}
			
			TeeTime session = new TeeTime("prestonwood.com");

			String response = "";

			if (session.login(obj.getUserid(), obj.getPassword())) {
				System.out.println("Logged in as " + obj.getUserid());
				
				try {
					Golfer golfer = session.getMemberInfo();

					JSONObject result = JsonGolfer(golfer);
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
