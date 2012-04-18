package de.lemo.dms.processing;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import de.lemo.dms.processing.parameter.ParameterMetaData;
import de.lemo.dms.processing.parameter.Parameters;

/**
 * Base question, provides access to parameter type and meta information.
 * 
 * @author Leonard Kappe
 */

@Produces(MediaType.APPLICATION_JSON)
public abstract class Question {

    protected abstract List<ParameterMetaData<?>> loadParamMetaData();

    public Parameters loadParameters() {
        String questionName = getClass().getCanonicalName();

        /*
         * Search for a single @GET annotated method (a REST resource).
         */

        Method compute = null;
        for (Method method : this.getClass().getMethods()) {
            if (method.getAnnotation(GET.class) != null) {
                if (compute != null) {
                    throw new RuntimeException("Duplicated @GET resource in question " + questionName + ". "
                            + "Questions must provide a single method annotated with " + GET.class.getCanonicalName()
                            + ".");
                }
                compute = method;
            }
        }
        if (compute == null) {
            throw new RuntimeException("No @GET resource found in " + questionName + ".");
        }

        /*
         * Validate parameter names and types.
         */

        /* map of actual method's parameters: <id, type> */
        Map<String, Class<?>> paramTypes = new HashMap<String, Class<?>>();

        Class<?>[] parameterTypes = compute.getParameterTypes();
        Annotation[][] parameterAnnotations = compute.getParameterAnnotations();
        for (int i = 0; i < parameterTypes.length; i++) {
            for (Annotation annotation : parameterAnnotations[i]) {
                if (annotation.annotationType().equals(QueryParam.class)) {
                    String parameterId = ((QueryParam) annotation).value();
                    paramTypes.put(parameterId, parameterTypes[i]);
                }
            }
        }

        /* map of parameter meta data description: <id, meta data> */
        Map<String, ParameterMetaData<?>> metaDataMap = new HashMap<String, ParameterMetaData<?>>();
        for (ParameterMetaData<?> paramMeta : loadParamMetaData()) {
            metaDataMap.put(paramMeta.getId(), paramMeta);
        }

        /*
         * Now check consistency of method parameter and meta data.
         */

        for (Entry<String, Class<?>> entry : paramTypes.entrySet()) {
            ParameterMetaData<?> paramMeta = metaDataMap.get(entry.getKey());
            if (paramMeta == null) {
                throw new RuntimeException("Missing meta data description for " + entry.getKey() + " in "
                        + questionName + ".");
            }
            if (paramMeta.getType().equals(Void.class)) {
                // Void indicates that we should use the actual parameter's type
                paramMeta.setType(entry.getValue());
            } else if (!paramMeta.getType().equals(entry.getValue())) {
                throw new RuntimeException("Type mismatch for parameter " + entry.getKey() + " in " + questionName
                        + ".");
            }
        }

        return new Parameters(metaDataMap.values());
    }

    protected boolean validateArguments(MultivaluedMap<String, String> arguments) {

        /*
         * XXX instead of returning false, we could throw an exception or even
         * send back a list of invalid arguments
         */

        for (ParameterMetaData<?> parameter : loadParamMetaData()) {
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
