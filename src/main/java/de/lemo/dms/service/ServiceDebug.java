package de.lemo.dms.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;

import de.lemo.dms.core.ConfigurationProperties;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.ChatLogMining;
import de.lemo.dms.db.miningDBclass.CourseLogMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;

@Path("/debug")
public class ServiceDebug extends BaseService {

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String debug() {

        StringBuilder html = new StringBuilder("<html><head><title>DMS Debug</title></head><body><h1>Debug</h1><ul>");

        IDBHandler dbHandler2 = config.getDBHandler();
        html.append("<li>dbHandler2: ").append(dbHandler2).append("</li>");
        html.append("<li>Config logger.level: ")
                .append(ConfigurationProperties.getString("mining.hibernate.connection.url"))
                .append("</li>");
        Session miningSession = null;
        if(dbHandler2 != null) {
            miningSession = dbHandler2.getMiningSession();
            html.append("<li>miningSession: ").append(miningSession).append("</li>");
        }
        if(miningSession != null) {
            html.append("<li>miningSession isConnected: ").append(miningSession.isConnected()).append("</li>");
            html.append("<li>miningSession isOpen: ").append(miningSession.isOpen()).append("</li>");

            List<?> result = null;
            // try {
            result = miningSession.createCriteria(AssignmentLogMining.class, "log").setMaxResults(3).list();
          
            result = miningSession.createCriteria(ResourceLogMining.class, "log").setMaxResults(3).list();
          
        
            result = miningSession.createCriteria(CourseLogMining.class, "log").setMaxResults(3).list();
           
            result = miningSession.createCriteria(ForumLogMining.class, "log").setMaxResults(3).list();
           
            result = miningSession.createCriteria(QuestionLogMining.class, "log").setMaxResults(3).list();
           
            result = miningSession.createCriteria(QuizLogMining.class, "log").setMaxResults(3).list();
           
            result = miningSession.createCriteria(ResourceLogMining.class, "log").setMaxResults(3).list();
           
            result = miningSession.createCriteria(ScormLogMining.class, "log").setMaxResults(3).list();
           
            result = miningSession.createCriteria(WikiLogMining.class, "log").setMaxResults(3).list();
         
            result = miningSession.createCriteria(ChatLogMining.class, "log").setMaxResults(3).list();
            
            
            // } catch (Exception exeption) {
            //
            // Throwable e = exeption;
            // while(e != null) {
            // html.append("<li>").append(e.getClass() + ": " +
            // e.getMessage()).append("<ul>");
            // for(StackTraceElement ste : e.getStackTrace()) {
            // html.append("<li>").append(ste.toString()).append("</li>");
            //
            // }
            // html.append("</li></ul></li>");
            // e = e.getCause();
            // }
            // }
            html.append("<li>ResourceLogMining result: ").append(result).append("</li>");
            if(result != null) {
                html.append("<li>ResourceLogMining results: ").append(result.size()).append("</li>");
            }

            miningSession.close();
        }

        return html.append("</ul></body></html>").toString();
    }
}
