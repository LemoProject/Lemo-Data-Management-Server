package de.lemo.dms.processing;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import de.lemo.dms.processing.parameter.Interval;
import de.lemo.dms.processing.parameter.ParameterMetaData;
import de.lemo.dms.processing.parameter.Parameter;

/**
 * Test question as example for parameter initializations. Should return some
 * result based on students within a range of ages of a particular course.
 * 
 */

@Path("q1")
public class TestQuestion1 extends Question {

    @Override
    protected List<ParameterMetaData<?>> loadParamMetaData() {
        List<ParameterMetaData<?>> parameters = new LinkedList<ParameterMetaData<?>>();
        Collections.<ParameterMetaData<?>> addAll(parameters,
                Interval.create(Integer.class, "maxage", "Maximum age", "", 18, 99, 99),
                Interval.create(Integer.class, "minage", "Minimum age", "", 18, 99, 18),
                Parameter.create("cid", "Course ID", "The ID of a course"));
        return parameters;
    }

    @GET
    public String compute(@QueryParam("maxage") Integer maxAge, @QueryParam("minage") Integer minAge,
            @QueryParam("cid") String courseId) {

        /*
         * Do a DB query here: Select users from the course where the age is
         * between [minage] and [maxage].
         */

        // test result
        String result = "test question 1 executed. Params: " + minAge + "/" + maxAge + "/" + courseId;
        return result;
    }

}
