package de.lemo.dms.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Service that provides a list of available questions.
 * 
 * @author Leonard Kappe
 * 
 */
@Path("catalog")
public class ServiceCatalog {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getQuestionCatalog() {
        return "";
    }

}
