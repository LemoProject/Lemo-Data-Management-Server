package de.lemo.dms.processing;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import de.lemo.dms.processing.parameter.ParameterMetaData;
import de.lemo.dms.processing.parameter.Parameters;
import de.lemo.dms.service.BaseService;

/**
 * Base question, provides access to parameter type and meta information of the
 * subclass.
 * 
 * @author Leonard Kappe
 */

@Produces(MediaType.APPLICATION_JSON)
public abstract class Question extends BaseService {

    /**
     * Implementations must provides additional information about the parameters
     * of the question. For each {@link QueryParam} annotated parameter of the
     * question's REST-interface method, a meta data object with the same ID and
     * type must be provided. A meta data object of the generic type
     * {@link ParameterMetaData}<{@link Void}> indicates that the type of the
     * method's parameter should (and will) be used.
     * 
     * 
     * @return A list of parameter meta data objects
     */
    protected abstract List<ParameterMetaData<?>> createParamMetaData();

    /**
     * Validates and retrieves parameter type information.
     * 
     * @return An XML/JSON convertible parameter object
     */
    public final Parameters getParameters() {
        String questionName = getClass().getCanonicalName();

        /*
         * Search for a single @GET/@POST annotated method (a REST resource).
         */

        Method compute = null;
        for(Method method : this.getClass().getMethods()) {
            if(method.getAnnotation(GET.class) != null || method.getAnnotation(POST.class) != null) {
                if(compute != null) {
                    throw new RuntimeException("Duplicated @GET/@POST resource in question " + questionName + ". "
                            + "Questions must provide a single method annotated with " + GET.class.getCanonicalName()
                            + ".");
                }
                compute = method;
            }
        }
        if(compute == null) {
            throw new RuntimeException("No @GET/@POST resource found in " + questionName + ".");
        }

        /*
         * Validate parameter names and types.
         */

        /* map of actual method's parameters: <id, type> */
        Map<String, Class<?>> paramTypes = new HashMap<String, Class<?>>();

        Class<?>[] parameterTypes = compute.getParameterTypes();
        Annotation[][] parameterAnnotations = compute.getParameterAnnotations();
        for(int i = 0; i < parameterTypes.length; i++) {
            for(Annotation annotation : parameterAnnotations[i]) {
                if(annotation.annotationType().equals(QueryParam.class)) {
                    String parameterId = ((QueryParam) annotation).value();
                    paramTypes.put(parameterId, parameterTypes[i]);
                } else if(annotation.annotationType().equals(QueryParam.class)) {
                    String parameterId = ((FormParam) annotation).value();
                    paramTypes.put(parameterId, parameterTypes[i]);
                }
            }
        }

        /* map of parameter meta data description: <id, meta data> */
        Map<String, ParameterMetaData<?>> metaDataMap = new HashMap<String, ParameterMetaData<?>>();
        for(ParameterMetaData<?> paramMeta : createParamMetaData()) {
            metaDataMap.put(paramMeta.getId(), paramMeta);
        }

        /*
         * Now check integrity of each method parameter and its related meta
         * data.
         */

        for(Entry<String, Class<?>> entry : paramTypes.entrySet()) {
            ParameterMetaData<?> paramMeta = metaDataMap.get(entry.getKey());
            if(paramMeta == null) {
                throw new RuntimeException("Missing meta data description for " + entry.getKey() + " in "
                        + questionName + ".");
            }
            if(paramMeta.getType().equals(Void.class)) {
                // Void indicates that we should use the actual parameter's type
                paramMeta.setType(entry.getValue());
            } else if(!paramMeta.getType().equals(entry.getValue())) {
                throw new RuntimeException("Type mismatch for parameter " + entry.getKey() + " in " + questionName
                        + ".");
            }
        }

        return new Parameters(metaDataMap.values());
    }

    // /**
    // *
    // *
    // * @param arguments
    // * A map of request arguments as provided by the parameter
    // * <code>@{@link Context} {@link UriInfo}</code>.
    // * @return
    // */
    // protected boolean validateArguments(MultivaluedMap<String, String>
    // arguments) {
    //
    // /*
    // * XXX instead of returning false, we could throw an exception or even
    // * send back a list of invalid arguments
    // */
    //
    // for (ParameterMetaData<?> parameter : createParamMetaData()) {
    // String argument = arguments.getFirst(parameter.getId());
    // if (argument == null) {
    // return false;
    // }
    // // if (!parameter.validateArgument(argument)) {
    // // return false;
    // // }
    // }
    //
    // return true;
    // }

}
