package de.lemo.dms.service;

import java.util.Map;

import de.lemo.dms.processing.Question;

/**
 * Used by services that need to operate with a collection of all questions.
 * 
 * @author Leonard Kappe
 */
public class QuestionBaseService extends BaseService {

    /**
     * Gets a Map
     * 
     * @return A map with the questio's id/path as key and the question's
     *         singleton as value.
     */
    protected Map<String, Question> getQuestions() {
        return config.getResourceConfig().getQuestionSingletons();
    }

}
