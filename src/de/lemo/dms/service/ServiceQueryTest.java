package de.lemo.dms.service;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import de.lemo.dms.processing.TestQuestion1;

/**
 * A test service that run a test question.
 * 
 * @author Leonard Kappe
 * 
 */
@Path("/test1")
public class ServiceQueryTest {

    @GET
    @Produces("text/html")
    public String startTimeHtml(
            @QueryParam("min_age") int minAge,
            @QueryParam("max_age") int maxAge,
            @QueryParam("course_id") String courseId) {

        // localhost:4443/test?min_age=20&max_age=40&course_id="c1"
        
        TestQuestion1 tq = new TestQuestion1();

        List<Object> arguments = new LinkedList<Object>();
        arguments.add(minAge);
        arguments.add(maxAge);
        arguments.add(courseId);

        tq.execute(arguments);

        String response = "<html><title>Query Test</title><body>";
        response += "<h1>TestQuestion1</h1>";
        response += "</body></html>";

        return response;

    }
}
