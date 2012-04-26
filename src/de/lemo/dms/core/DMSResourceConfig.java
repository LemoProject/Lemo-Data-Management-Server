package de.lemo.dms.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.Path;

import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.PackagesResourceConfig;

import de.lemo.dms.processing.Question;

/**
 * Resource configuration for the DMS, used to discover (and auto load) web
 * services as well as to create singletons of question service classes in
 * different locations.
 * 
 * @author Leonard Kappe
 * 
 */
public class DMSResourceConfig extends DefaultResourceConfig {

    private Map<String, Question> questionSingletons;

    // let delegates do the dirty work (scanning packages for resources)
    private PackagesResourceConfig serviceScanner;
    private PackagesResourceConfig questionScanner;

    public DMSResourceConfig(Package services, Package questions) {
        serviceScanner = new PackagesResourceConfig(services.getName());
        questionScanner = new PackagesResourceConfig(questions.getName());
    }

    @Override
    public Set<Class<?>> getRootResourceClasses() {
        return serviceScanner.getRootResourceClasses();
    }

    @Override
    public Set<Object> getSingletons() {
        return this.getRootResourceSingletons();
    }

    @Override
    public Set<Object> getRootResourceSingletons() {
        if (questionSingletons == null) {
            try {
                questionSingletons = createSingletons();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return new HashSet<Object>(questionSingletons.values());
    }

    /**
     * Gets an unmodifiable map of question singletons.
     * 
     * @return a map of question singleton services, keyed by their paths.
     */
    public Map<String, Question> getQuestionSingletons() {
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
        for (Entry<String, Class<? extends Question>> entry : getQuestionResources().entrySet()) {
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
        for (Class<?> resource : questionScanner.getRootResourceClasses()) {
            if (Question.class.isAssignableFrom(resource)) {
                String path = resource.getAnnotation(Path.class).value();
                questions.put(path, (Class<Question>) resource);
            }
        }
        return questions;
    }

}
