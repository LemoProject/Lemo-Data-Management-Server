/**
 * File ./src/main/java/de/lemo/dms/service/ServiceDebug.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/service/ServiceDebug.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.IDBHandler;
import de.lemo.dms.db.mapping.CollaborationLog;
import de.lemo.dms.db.mapping.AssessmentLog;
import de.lemo.dms.db.mapping.AccessLog;

/**
 * Service for debugging messages
 * 
 * @author Leonard Kappe
 * @author Sebastian Schwarzrock
 */
@Path("/debug")
public class ServiceDebug {

	private static void appendItem(final StringBuilder sb, final String itemName, final Object item) {
		sb.append("<dt>");
		if (item == null) {
			sb.append("<i class='icon-exclamation-sign'></i> ")
					.append("<span class='label label-error'>").append(itemName).append("</span>");

		} else {
			sb.append("<i class='icon-ok-sign'></i> ")
					.append("<span class='label label-success'>").append(itemName).append("</span>");
		}
		sb.append("</dt><dd><code>").append(item).append("</code></dt>");
	}

	private static void appendUnkownItem(final StringBuilder sb, final String itemName) {
		sb.append("<dt>")
				.append("<i class='icon-question-sign'></i> ")
				.append("<span class='label'>").append(itemName).append("</span> ")
				.append("</dt>")
				.append("<dd> <code>?</code> </dd>");

	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String debug() {

		final StringBuilder content = new StringBuilder("<h1>DMS Server Info</h1>");
		content.append("<div class='page-header'><h3>Profile: ")
				.append(ServerConfiguration.getInstance().getName())
				.append("</h2></div><dl class='dl-horizontal'>");
		IDBHandler dbHandler = null;
		try {
			dbHandler = ServerConfiguration.getInstance().getMiningDbHandler();
		} catch (final ExceptionInInitializerError e) {
			content.append("<div class='alert alert-error'><b>ExceptionInInitializerError</b>");
			this.printExceptionStack(content, e);
			content.append("</div>");
		}

		ServiceDebug.appendItem(content, "database handler", dbHandler);

		Session miningSession = null;
		if (dbHandler == null) {
			ServiceDebug.appendUnkownItem(content, "mining session");
			ServiceDebug.appendUnkownItem(content, "session connected");
			ServiceDebug.appendUnkownItem(content, "test query");
		} else {
			miningSession = dbHandler.getMiningSession();
			ServiceDebug.appendItem(content, "mining session", miningSession);
			if (miningSession == null) {
				ServiceDebug.appendUnkownItem(content, "session connected");
				ServiceDebug.appendUnkownItem(content, "test query");
			} else {
				ServiceDebug
						.appendItem(content, "session connected", miningSession.isConnected() ? Boolean.TRUE : null);
				List<?> result = null;
				int resultCount = 0;
				try {
					result = miningSession.createCriteria(AccessLog.class, "log").setMaxResults(1).list();
					resultCount += result.size();
					result = miningSession.createCriteria(AssessmentLog.class, "log").setMaxResults(1).list();
					resultCount += result.size();
					result = miningSession.createCriteria(CollaborationLog.class, "log").setMaxResults(1).list();
					resultCount += result.size();
				} catch (final Exception exeption) {

					ServiceDebug.appendItem(content, "test query", null);
					this.printExceptionStack(content, exeption);
				}

				if (result != null) {
					ServiceDebug.appendItem(content, "test query", "results: " + resultCount);
				} else {
					ServiceDebug.appendItem(content, "test query", result);
				}

				miningSession.close();
			}
		}
		content.append("</dl>");

		final StringBuilder html = new StringBuilder(
				"<!DOCTYPE html> <html> <head>"
						+ "<link href='//netdna.bootstrapcdn.com/twitter-bootstrap/2.1.1/css/bootstrap-combined.min.css' rel='stylesheet'>"
						+ "<title>DMS Debug</title>"
						+ "</head>"
						+ "<body> <div class='container'>").append(content).append("<div> </body> </html>");

		return html.toString();
	}

	private void printExceptionStack(final StringBuilder content, Throwable throwable) {
		while (throwable != null) {
			content.append("<li>").append(throwable.getClass() + ": " +
					throwable.getMessage()).append("<ul>");
			for (final StackTraceElement ste : throwable.getStackTrace()) {
				content.append("<li>").append(ste.toString()).append("</li>");

			}
			content.append("</li></ul></li>");
			throwable = throwable.getCause();
		}
	}
}
