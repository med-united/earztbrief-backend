package health.medunited;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/makeTest")
public class TestController {

    @GET
    public String performTest() {
        return "Test6";
    }
    
}
