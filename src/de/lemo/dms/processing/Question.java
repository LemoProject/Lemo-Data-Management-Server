package de.lemo.dms.processing;

import java.lang.reflect.Method;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import de.lemo.dms.processing.parameter.Parameter;

/**
 * Generic question.
 * 
 * @author Leonard Kappe
 */

@Produces(MediaType.APPLICATION_JSON)
public abstract class Question {

    protected abstract List<Parameter<?>> getParameterDescription();

    public List<Parameter<?>> getParameters() {

        Method compute = null;

        for (Method method : this.getClass().getMethods()) {
            if (method.getAnnotation(GET.class) != null) {
                if (compute != null) {
                    throw new RuntimeException("duplicate GET webservice found");
                }
                compute = method;
            }
        }
        if (compute == null) {
            throw new RuntimeException("no GET webservice found");
        }

        List<Parameter<?>> parameterDescription = getParameterDescription();
        Class<?>[] parameterTypes = compute.getParameterTypes();

        if (parameterDescription == null || parameterTypes.length != parameterDescription.size()) {
            throw new RuntimeException("'compute' method parameters/description count mismatch");
        }

        return parameterDescription;
    }

    protected boolean validateArguments(MultivaluedMap<String, String> arguments) {

        /*
         * XXX instead of returning false, we could throw an exception or even
         * send back a list of invalid arguments
         */

        for (Parameter<?> parameter : getParameterDescription()) {
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
