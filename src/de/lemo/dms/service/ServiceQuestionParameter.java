package de.lemo.dms.service;

import java.util.Map;

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
public class ServiceQuestionParameter extends BaseService {

    @GET
    public Parameters getParameter(@PathParam("qid") String questionId) {
        Map<String, Question> questions = config.getResourceConfig().getQuestionSingletons();
        if (!questions.containsKey(questionId)) {
            logger.warn("question " + questionId + " not found");
            // throw web exception
            return null;
        }
        return questions.get(questionId).getParameters();
    }

}
