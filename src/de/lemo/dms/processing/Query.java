package de.lemo.dms.processing;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import de.lemo.dms.processing.parameter.Parameter;

/**
 * Generic question.
 * 
 * @author Leonard Kappe
 */
public abstract class Query {

    private List<Parameter<?>> parameters;

    public Object execute(List<?> arguments) {
        validateArguments(arguments);
        return null;
    }

    protected void setParameters(List<Parameter<?>> parameters) {
        this.parameters = parameters;
    }

    protected boolean validateArguments(List<?> arguments) {

        if (arguments.size() != parameters.size()) {
            return false;
        }

        /*
         * instead of returning false, we could throw an exception with some
         * more info here
         */

        Iterator<?> i = arguments.iterator();
        for (Parameter<?> parameter : parameters) {
            Object argument = i.hasNext() ? i.next() : null;
            if (!parameter.validateArgument(argument)) {
                return false;
            }
        }

        return true;
    }
}
