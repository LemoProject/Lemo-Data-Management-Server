package de.lemo.dms.processing;

import java.util.LinkedList;
import java.util.List;

import de.lemo.dms.processing.parameter.Interval;
import de.lemo.dms.processing.parameter.Item;
import de.lemo.dms.processing.parameter.Parameter;

/**
 * Test question as example for parameter initializations. Should return some
 * result based on students within a range of ages of a particular course.
 * 
 */
public class TestQuestion1 extends Query {

    public TestQuestion1() {

        // create parameters

        Parameter<Integer> minAge = new Interval<Integer>("Minimum age", "The minimum age of the students", 18, 99);
        Parameter<Integer> maxAge = new Interval<Integer>("Maximum age", "The maximum age of the students", 18, 99);
        Parameter<String> course = new Item<String>("Course ID", "The ID of a course") {
            // This probably shouldn't be an inline class
            @Override
            protected List<String> getValidValues() {
                /*
                 * Do a DB query here: Select courses where the current user is
                 * a course admin.
                 */
                LinkedList<String> list = new LinkedList<String>();
                list.add("c1");
                list.add("c3");
                return list;
            }
        };

        // set parameters
        List<Parameter<?>> parameters = new LinkedList<Parameter<?>>();
        parameters.add(minAge);
        parameters.add(maxAge);
        parameters.add(course);
        setParameters(parameters);
    }

    @Override
    public Object execute(List<?> arguments) {
        validateArguments(arguments);

        /*
         * Do a DB query here: Select users from the course where the age is
         * between [start] and [end].
         */

        return null;
    }
}
