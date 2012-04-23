package de.lemo.dms.service;

import java.util.HashMap;
import java.util.Set;

import javax.ws.rs.Path;

import de.lemo.dms.processing.Question;

public class QuestionBaseService extends ServiceBaseService {

    /**
     * Searches through the currently available root resources (REST services)
     * and returns a map of paths and services that extend {@link Question}.
     * 
     * @return A map with the question id/path as key and the class type as
     *         value.
     */
    @SuppressWarnings("unchecked")
    protected HashMap<String, Class<? extends Question>> getQuestions() {
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

}
