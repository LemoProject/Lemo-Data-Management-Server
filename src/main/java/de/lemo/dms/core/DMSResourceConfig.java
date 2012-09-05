package de.lemo.dms.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.Path;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.common.collect.Maps;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.PackagesResourceConfig;

/**
 * Resource configuration for the DMS, used to discover (and auto load) web services as well as to create singletons of
 * question service classes in different locations.
 * 
 * @author Leonard Kappe
 * 
 */

public class DMSResourceConfig extends DefaultResourceConfig {

    public static final String QUESTION_BASE_PATH = "/dms/questions/";
    public static final String SERVICE_BASE_PATH = "/dms/services/";

    private Map<String, Object> resourceSingletons;

    private PackagesResourceConfig serviceScanner = new PackagesResourceConfig("de.lemo.dms.service");
    private PackagesResourceConfig questionScanner = new PackagesResourceConfig("de.lemo.dms.processing.questions");

    @Override
    public Map<String, Object> getExplicitRootResources() {
        return getResourceSingletons();
    }

    /**
     * Gets an unmodifiable map of question singletons.
     * 
     * @return a map of question singleton services, keyed by their paths.
     */
    public Map<String, Object> getResourceSingletons() {
        if(resourceSingletons == null) {
            try {
                resourceSingletons = createResourceSingletons();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return resourceSingletons;
    }

    /**
     * 
     * 
     * @return Resource singletons, mapped to their paths.
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private Map<String, Object> createResourceSingletons() throws InstantiationException, IllegalAccessException {
        Builder<String, Object> singletons = ImmutableMap.builder();
        for(Entry<String, Class<?>> entry : getQuestionResources().entrySet()) {
            singletons.put(entry.getKey(), entry.getValue().newInstance());
        }
        for(Entry<String, Class<?>> entry : getServiceResources().entrySet()) {
            singletons.put(entry.getKey(), entry.getValue().newInstance());
        }
        return singletons.build();
    }

    private HashMap<String, Class<?>> getQuestionResources() {
        HashMap<String, Class<?>> questions = Maps.newHashMap();
        for(Class<?> resource : questionScanner.getClasses()) {
            Path annotation = resource.getAnnotation(Path.class);
            questions.put(QUESTION_BASE_PATH + StringUtils.strip(annotation.value(), "/"), resource);
        }
        return questions;
    }

    private HashMap<String, Class<?>> getServiceResources() {
        HashMap<String, Class<?>> services = Maps.newHashMap();
        for(Class<?> resource : serviceScanner.getClasses()) {
            Path annotation = resource.getAnnotation(Path.class);
            services.put(SERVICE_BASE_PATH + StringUtils.strip(annotation.value(), "/"), resource);
        }
        return services;
    }

}
