package application.data;

import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONObject;

public class Member {

	private String userid;
	private String password;
	
	public Member(String userid, String password) {
		this.userid = userid;
		this.password = password;
	}
	
	/**
	 * Takes a JSON POST body string and parses it into a Reservation object
	 * Does validation of the input object so there is a well formed set of 
	 * reservation data for this API call.
	 * 
	 * userid	: string representing userid on tee time system
	 * password	: string representing password on tee time system
	 * 
	 * @param body the content the POST request in JSON format
	 * @return a new Reservation object filled in with the parsed data
	 */
	public static Member parse(String body) {
		
		try {
			JSONObject details = (JSONObject)JSON.parse(body);
			
			String userid = details.get("userid").toString();
			String password = details.get("password").toString();
			
			System.out.println("User: " + userid);
			System.out.println("Password: " + password);
			
			return new Member( userid, password);
		} catch(Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
		
}
