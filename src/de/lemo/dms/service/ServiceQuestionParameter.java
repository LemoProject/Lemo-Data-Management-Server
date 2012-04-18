package de.lemo.dms.service;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.TestQuestion1;
import de.lemo.dms.processing.parameter.Parameters;

/**
 * Service provides meta data about a question's parameters.
 * 
 * @author Leonard Kappe
 * 
 */
@Path("parameters/{qid}")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceQuestionParameter {

    private Logger logger = ServerConfigurationHardCoded.getInstance().getLogger();

    private static HashMap<String, Class<? extends Question>> questions;
    static {
        /*
         * TODO make this dynamic, using a question manager
         */
        questions = new HashMap<String, Class<? extends Question>>();
        questions.put("q1", TestQuestion1.class);
    }

    @GET
    public Parameters getParameter(@PathParam("qid") String questionId) {
        if (!questions.containsKey(questionId)) {
            logger.warn("question " + questionId + " not found");
            // throw web exception
            return null;
        }

        try {
            return questions.get(questionId).newInstance().loadParameters();
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
