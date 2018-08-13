package application.data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ibm.json.java.JSON;
import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;

import teetime.Golfer;
import teetime.Golfers;
import teetime.CoursePreferences;

public class Reservation {

	private String userid;
	private String password;
	private Date teetime;
	private CoursePreferences courses;

	private Golfers golfers;
	
	public Reservation(String userid, String password, Date teetime, CoursePreferences courses, Golfers golfers) {
		this.userid = userid;
		this.password = password;
		this.teetime = teetime;
		this.setCourses(courses);
		this.golfers = golfers;
	}
	
	/**
	 * Get the date specified by str in a format like this:
	 * 8:00 AM 08/14/2017
	 * 
	 * @param str the date string to parse
	 * @return a date object for the time specified in str
	 */
	private static Date parseDate( String str ) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("h:mm a MM/dd/yyyy");
		Date theDate = formatter.parse( str );
		
		return theDate;
	}

	/**
	 *  parse a JSON array of courses to include in the search 
	 *  this will be an array of strings, e.g. [ "Highlands", "Meadows", "Fairways" ]
	 *  
	 * @param json
	 * @return a CoursePreferences object with the list of courses
	 */
	private static CoursePreferences parseCourses(JSONObject json) {
		JSONArray jsonCourses = (JSONArray)json.get("courses");
		String coursePrefs[] = { "any", "none", "none" };
		
		if (jsonCourses.size()>0) {
			for (int i=0; i<Math.min(coursePrefs.length, jsonCourses.size()); i++) {
				coursePrefs[i] = (String)jsonCourses.get(i);
			}
		}

		CoursePreferences courses = new CoursePreferences(coursePrefs[0], coursePrefs[1], coursePrefs[2]);
		System.out.println("Course Prefs: " + courses);
		
		return courses;
	}
	
	// 
	// Numbers in particular can come in via JSON as either a Number object
	// or a String.  We coerce whatever is parsed into a String for consistency
	//
	private static String parseAsString(Object obj) {
		if (obj==null) {
			return null;
		}
		
		return obj.toString();
	}

	/**
	 * 	parse a JSON array of golfers.  each golfer has a format like this:
	 *  { "name" : "Donald Boulia", "id" : "123456" }
	 *  
	 * @param json
	 * @return an array of Golfer objects
	 */
	private static Golfers parseGolfers(JSONObject json) {
		Golfers golfers = new Golfers();
		
		JSONArray jsonGolfers = (JSONArray)json.get("golfers");
		
		for (int i=0; i<jsonGolfers.size(); i++) {
			JSONObject jsonGolfer = (JSONObject)jsonGolfers.get(i);
			String name =  (String)jsonGolfer.get("name");
			String id = parseAsString(jsonGolfer.get("id"));
			
			Golfer golfer = new Golfer(name, id);
			System.out.println("Found Golfer " + golfer.toString());

			golfers.add(golfer);
		}

		return golfers;
	}
	
	/**
	 * Takes a JSON POST body string and parses it into a Reservation object
	 * Does validation of the input object so there is a well formed set of 
	 * reservation data for this API call.
	 * 
	 * userid	: string representing userid on tee time system
	 * password	: string representing password on tee time system
	 * time		: string representing date in a format like 8:00 AM 9/21/2017
	 * courses	: array of strings, each string represents an ordered list of courses to include in reservation
	 * golfers	: array of objects. each object contains golfers name and id
	 * 
	 * @param body the content the POST request in JSON format
	 * @return a new Reservation object filled in with the parsed data
	 */
	public static Reservation parse(String body) {
		
		try {
			JSONObject details = (JSONObject)JSON.parse(body);
			
			String userid = details.get("userid").toString();
			String password = details.get("password").toString();
			String time = details.get("time").toString();
			
			System.out.println("User: " + userid);
			System.out.println("Password: " + password);
			System.out.println("Time: " + time);
			
			Date teetime = parseDate(time);

			CoursePreferences courses = parseCourses(details);
			
			Golfers golfers = parseGolfers(details);
			
			return new Reservation( userid, password, teetime, courses, golfers);
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
	public Date getTeetime() {
		return teetime;
	}
	public void setTeetime(Date teetime) {
		this.teetime = teetime;
	}
	public Golfers getGolfers() {
		return golfers;
	}
	public void setGolfers(Golfers golfers) {
		this.golfers = golfers;
	}
	
	/**
	 * see if this reservation contains any golfers
	 * these would be the golfers added to reservation when a teetime is booked
	 * 
	 * There must be at least one golfer in the list in order to book
	 * a reservation.
	 * 
	 * @return true if there are golfers in the reservation, false if none exist
	 */
	public boolean hasGolfers() {
		return golfers.size() > 0;
	}

	public CoursePreferences getCourses() {
		return courses;
	}

	public void setCourses(CoursePreferences courses) {
		this.courses = courses;
	}
	
}
