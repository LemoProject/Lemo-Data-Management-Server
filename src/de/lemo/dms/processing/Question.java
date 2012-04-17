package de.lemo.dms.processing;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import de.lemo.dms.processing.parameter.Parameter;

/**
 * Generic question.
 * 
 * @author Leonard Kappe
 */
public abstract class Question {

    public abstract List<?> compute(MultivaluedMap<String, String> arguments);

    public abstract List<Parameter<?>> getParameters();

    protected boolean validateArguments(MultivaluedMap<String, String> arguments) {

        /*
         * XXX instead of returning false, we could throw an exception or even
         * send back a list of invalid arguments
         */

        for (Parameter<?> parameter : getParameters()) {
            String argument = arguments.getFirst(parameter.getId());
            if (argument == null) {
                return false;
            }
            // if (!parameter.validateArgument(argument)) {
            // return false;
            // }
        }

        return true;
    }
}
