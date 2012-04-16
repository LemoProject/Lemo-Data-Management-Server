package de.lemo.dms.processing;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import de.lemo.dms.processing.parameter.Interval;
import de.lemo.dms.processing.parameter.Parameter;

/**
 * Test question as example for parameter initializations. Should return some
 * result based on students within a range of ages of a particular course.
 * 
 */
public class TestQuestion1 extends Question {

    public TestQuestion1() {
        List<Parameter<?>> parameters = new LinkedList<Parameter<?>>();
        Parameter<Integer> minAge = new Interval<Integer>("maxage", "Minimum age", "", 18, 99, 18);
        Parameter<Integer> maxAge = new Interval<Integer>("minage", "Maximum age", "", 18, 99, 99);
        Parameter<String> courseId = new Parameter<String>("cid", "Course ID", "The ID of a course");
        Collections.<Parameter<?>> addAll(parameters, minAge, maxAge, courseId);
        setParameters(parameters);
    }

    @Override
    public List<?> compute(MultivaluedMap<String, String> arguments) {
        if (!validateArguments(arguments)) {
            // TODO throw exception
            return null;
        }

        /*
         * Do a DB query here: Select users from the course where the age is
         * between [minage] and [maxage].
         */
        List<String> result = new LinkedList<String>();
        result.add("test question 1 executed");
        result.add("all arguments were valid");
        return result;
    }
}
