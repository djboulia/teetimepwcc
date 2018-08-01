package application.rest.v1;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;



@Path("v1/example")
public class Example {


    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response example() {
        return Response.ok("success").build();
    }


}
