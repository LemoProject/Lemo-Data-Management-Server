package de.lemo.dms.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.Path;

import com.sun.jersey.api.core.PackagesResourceConfig;

import de.lemo.dms.processing.Question;

/**
 * Resource config for the DMS, used to discover (and autoload) webservices in
 * different locations.
 * 
 * @author Leonard Kappe
 * 
 */
public class DMSResourceConfig extends PackagesResourceConfig {

    private Map<String, Question> questionSingletons;

    public DMSResourceConfig() {
        super("de.lemo.dms");
    }

    @Override
    public Set<Object> getSingletons() {
        return getRootResourceSingletons();
    }

    @Override
    public Set<Object> getRootResourceSingletons() {
        if (questionSingletons == null) {
            try {
                questionSingletons = createSingletons();
            } catch (InstantiationException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return new HashSet<Object>(questionSingletons.values());
    }

    public Map<String, Question> getQuestionSingletons() {
        return questionSingletons;
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
        for (Class<?> resource : getRootResourceClasses()) {
            if (Question.class.isAssignableFrom(resource)) {
                String path = resource.getAnnotation(Path.class).value();
                questions.put(path, (Class<Question>) resource);
            }
        }
        return questions;
    }

    private Map<String, Question> createSingletons() throws InstantiationException, IllegalAccessException {
        HashMap<String, Question> singletons = new HashMap<String, Question>();
        for (Entry<String, Class<? extends Question>> entry : getQuestionResources().entrySet()) {
            singletons.put(entry.getKey(), entry.getValue().newInstance());
        }
        return Collections.unmodifiableMap(singletons);
    }
}
