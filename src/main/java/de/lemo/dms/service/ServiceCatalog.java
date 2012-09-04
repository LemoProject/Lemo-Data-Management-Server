package de.lemo.dms.service;

import static de.lemo.dms.core.DMSResourceConfig.QUESTION_BASE_PATH;

import java.util.Map.Entry;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sun.grizzly.Context;

import de.lemo.dms.processing.Question;

/**
 * Service that provides a list of available questions.
 * 
 * @author Leonard Kappe
 * 
 */
@Path(QUESTION_BASE_PATH)
public class ServiceCatalog extends BaseService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getQuestionCatalog() {
        return "";
    }


//    @GET
//    @Produces(MediaType.TEXT_HTML)
//    public String getQuestionCatalogHtml() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("<!DOCTYPE html> <html> <body> <h1>The Question Catalog</h1> ");
//        sb.append("<table border = \"1\"> <thead>  <td><b>Question Path</b></td>  <td><b>Question Name</b></td>"
//                + " </thead>\n");
//        for(Entry<String, Question> entry : config.getResourceConfig().getQuestionSingletons().entrySet()) {
//            sb.append("<tr> <td>").append(entry.getKey().toString()).append("</td> <td>")
//                    .append(entry.getValue().getParameters().toString()).append("</td> </tr>\n");
//        }
//        sb.append(" <table> </html> </body>");
//        return sb.toString();
//    }
}
