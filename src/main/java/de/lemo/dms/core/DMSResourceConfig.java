package de.lemo.dms.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.ApplicationPath;

import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.PackagesResourceConfig;

import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.QuestionID;

/**
 * Resource configuration for the DMS, used to discover (and auto load) web
 * services as well as to create singletons of question service classes in
 * different locations.
 * 
 * @author Leonard Kappe
 * 
 */

public class DMSResourceConfig extends DefaultResourceConfig {

    public static final String QUESTION_BASE_PATH = "/questions/";

    private Map<String, Question> questionSingletons;

    // let delegates do the dirty work (scanning packages for resources)
    private PackagesResourceConfig serviceScanner = new PackagesResourceConfig("de.lemo.dms.service");
    private PackagesResourceConfig questionScanner = new QuestionResourceConfig("de.lemo.dms.processing.questions");

    @Override
    public Set<Class<?>> getRootResourceClasses() {
        return serviceScanner.getRootResourceClasses();
    }

    @Override
    public Set<Object> getSingletons() {
        return new HashSet<Object>(getQuestionSingletons().values());
    }

    @Override
    public Map<String, Object> getExplicitRootResources() {
        return new HashMap<String, Object>(getQuestionSingletons());
    }

    /**
     * Gets an unmodifiable map of question singletons.
     * 
     * @return a map of question singleton services, keyed by their paths.
     */
    public Map<String, Question> getQuestionSingletons() {
        if(questionSingletons == null) {
            try {
                questionSingletons = createSingletons();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return questionSingletons;
    }

    /**
     * Creates a singleton of each question in the specified package.
     * 
     * @return Question service singletons, mapped to their paths.
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private Map<String, Question> createSingletons() throws InstantiationException, IllegalAccessException {
        HashMap<String, Question> singletons = new HashMap<String, Question>();
        for(Entry<String, Class<? extends Question>> entry : getQuestionResources().entrySet()) {
            singletons.put(entry.getKey(), entry.getValue().newInstance());
        }
        return Collections.unmodifiableMap(singletons);
    }

    /**
     * Searches through the currently available root resources (REST services)
     * and returns a map of paths and services that extend {@link Question}.
     * 
     * @return A map with the question id/path as key and the class type as
     *         value.
     */
    @SuppressWarnings("unchecked")
    private HashMap<String, Class<? extends Question>> getQuestionResources() {
        HashMap<String, Class<? extends Question>> questions = new HashMap<String, Class<? extends Question>>();
        for(Class<?> resource : questionScanner.getClasses()) {
            if(Question.class.isAssignableFrom(resource)) {
                Class<Question> question = (Class<Question>) resource;
                String questionID = resource.getAnnotation(QuestionID.class).value().trim();
                questions.put(QUESTION_BASE_PATH + questionID, question);
            }
        }
        return questions;
    }

}
