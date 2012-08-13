package de.lemo.dms.service;

import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.parameter.Parameters;
import de.lemo.dms.service.responses.ResourceNotFoundException;
import static de.lemo.dms.core.DMSResourceConfig.QUESTION_BASE_PATH;

/**
 * Service provides meta data about a question's parameters.
 * 
 * @author Leonard Kappe
 * 
 */
@Path(QUESTION_BASE_PATH + "parameters/{qid}")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceQuestionParameter extends BaseService {

    @GET
    public Parameters getParameter(@PathParam("qid") String questionId) {
        Map<String, Question> questions = config.getResourceConfig().getQuestionSingletons();
        String path = QUESTION_BASE_PATH + questionId;
        if(!questions.containsKey(path)) {
            logger.warn("Question '" + path + "' not found.");
            throw new ResourceNotFoundException();
        }
        logger.info("Getting parameters of'" + path + "'.");
        return questions.get(path).getParameters();
    }

}
