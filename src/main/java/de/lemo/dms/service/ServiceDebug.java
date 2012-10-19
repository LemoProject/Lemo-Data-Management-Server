package de.lemo.dms.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;

import de.lemo.dms.core.ApplicationProperties;
import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.CourseLogMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;

@Path("/debug")
public class ServiceDebug {

    private static final void appendItem(StringBuilder sb, String itemName, Object item) {
        sb.append("<dt>");
        if(item == null) {
            sb.append("<i class='icon-exclamation-sign'></i> ")
                    .append("<span class='label label-error'>").append(itemName).append("</span>");

        } else {
            sb.append("<i class='icon-ok-sign'></i> ")
                    .append("<span class='label label-success'>").append(itemName).append("</span>");
        }
        sb.append("</dt><dd><code>").append(item).append("</code></dt>");
    }

    private static final void appendUnkownItem(StringBuilder sb, String itemName) {
        sb.append("<dt>")
                .append("<i class='icon-question-sign'></i> ")
                .append("<span class='label'>").append(itemName).append("</span> ")
                .append("</dt>")
                .append("<dd> <code>?</code> </dd>");

    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String debug() {

        StringBuilder content = new StringBuilder("<h1>DMS Server Info</h1>");
        content.append("<div class='page-header'><h3>Profile: ")
                .append(ApplicationProperties.getPropertyValue("lemo.display-name"))
                .append(" <small>[")
                .append(ApplicationProperties.getPropertyValue("lemo.system-name"))
                .append("]</small></h2></div><dl class='dl-horizontal'>");
        IDBHandler dbHandler = null;
        try {
            dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
        } catch (ExceptionInInitializerError e) {
            content.append("<div class='alert alert-error'><b>ExceptionInInitializerError</b>");
            printExceptionStack(content, e);
            content.append("</div>");
        }

        appendItem(content, "database handler", dbHandler);

        Session miningSession = null;
        if(dbHandler == null) {
            appendUnkownItem(content, "mining session");
            appendUnkownItem(content, "session connected");
            appendUnkownItem(content, "test query");
        } else {
            miningSession = dbHandler.getMiningSession();
            appendItem(content, "mining session", miningSession);
            if(miningSession == null) {
                appendUnkownItem(content, "session connected");
                appendUnkownItem(content, "test query");
            } else {
                appendItem(content, "session connected", miningSession.isConnected() ? Boolean.TRUE : null);
                List<?> result = null;
                int resultCount = 0;
                try {
                    result = miningSession.createCriteria(AssignmentLogMining.class, "log").setMaxResults(1).list();
                    resultCount += result.size();
                    result = miningSession.createCriteria(ResourceLogMining.class, "log").setMaxResults(1).list();
                    resultCount += result.size();
                    result = miningSession.createCriteria(CourseLogMining.class, "log").setMaxResults(1).list();
                    resultCount += result.size();
                    result = miningSession.createCriteria(ForumLogMining.class, "log").setMaxResults(1).list();
                    resultCount += result.size();
                    result = miningSession.createCriteria(QuestionLogMining.class, "log").setMaxResults(1).list();
                    resultCount += result.size();
                    result = miningSession.createCriteria(QuizLogMining.class, "log").setMaxResults(1).list();
                    resultCount += result.size();
                    result = miningSession.createCriteria(ResourceLogMining.class, "log").setMaxResults(1).list();
                    resultCount += result.size();
                    result = miningSession.createCriteria(ScormLogMining.class, "log").setMaxResults(1).list();
                    resultCount += result.size();
                    result = miningSession.createCriteria(WikiLogMining.class, "log").setMaxResults(1).list();
                    resultCount += result.size();
                } catch (Exception exeption) {

                    appendItem(content, "test query", null);
                    printExceptionStack(content, exeption);
                }

                if(result != null) {
                    appendItem(content, "test query", "results: " + resultCount);
                } else {
                    appendItem(content, "test query", result);
                }

                miningSession.close();
            }
        }
        content.append("</dl>");

        StringBuilder html = new StringBuilder(
                "<!DOCTYPE html> <html> <head>"
                        + "<link href='//netdna.bootstrapcdn.com/twitter-bootstrap/2.1.1/css/bootstrap-combined.min.css' rel='stylesheet'>"
                        + "<title>DMS Debug</title>"
                        + "</head>"
                        + "<body> <div class='container'>").append(content).append("<div> </body> </html>");

        return html.toString();
    }

    private void printExceptionStack(StringBuilder content, Throwable throwable) {
        while(throwable != null) {
            content.append("<li>").append(throwable.getClass() + ": " +
                    throwable.getMessage()).append("<ul>");
            for(StackTraceElement ste : throwable.getStackTrace()) {
                content.append("<li>").append(ste.toString()).append("</li>");

            }
            content.append("</li></ul></li>");
            throwable = throwable.getCause();
        }
    }
}
