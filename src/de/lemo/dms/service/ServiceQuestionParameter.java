package de.lemo.dms.service;

import java.util.HashMap;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.parameter.Parameters;

/**
 * Service provides meta data about a question's parameters.
 * 
 * @author Leonard Kappe
 * 
 */
@Path("parameters/{qid}")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceQuestionParameter extends ServiceBaseService {

    /**
     * Searches through the currently available root resources (REST services)
     * and returns a map of paths and services that extend {@link Question}.
     * 
     * @return A map with the question id/path as key and the class type as
     *         value.
     */
    @SuppressWarnings("unchecked")
    private HashMap<String, Class<? extends Question>> getQuestions() {
        HashMap<String, Class<? extends Question>> questions = new HashMap<String, Class<? extends Question>>();
        Set<Class<?>> resources = config.getResourceConfig().getRootResourceClasses();
        for (Class<?> resource : resources) {
            if (Question.class.isAssignableFrom(resource)) {
                String path = resource.getAnnotation(Path.class).value();
                questions.put(path, (Class<Question>) resource);
            }
        }
        logger.debug("Resources: " + resources);
        logger.debug("Question Resources: " + questions);
        return questions;
    }

    @GET
    public Parameters getParameter(@PathParam("qid") String questionId) {
        HashMap<String, Class<? extends Question>> questions = getQuestions();
        if (!questions.containsKey(questionId)) {
            logger.warn("question " + questionId + " not found");
            // throw web exception
            return null;
        }

        try {
            return questions.get(questionId).newInstance().createParameters();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
