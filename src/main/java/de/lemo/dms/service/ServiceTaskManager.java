/**
 * File ./src/main/java/de/lemo/dms/service/ServiceTaskManager.java
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

package de.lemo.dms.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.ExecutionException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.apache.log4j.Logger;
import de.lemo.dms.processing.AnalysisTask;
import de.lemo.dms.processing.AnalysisTaskManager;

@Path("tasks")
public class ServiceTaskManager {

	public static final String TASK_POLLING_PATH = "services/tasks/";
	private final Logger logger = Logger.getLogger(this.getClass());

	@GET
	@Path("{id}")
	public Response taskResult(@PathParam("id") String taskId) throws UnsupportedEncodingException {

		logger.debug("Lookup status of Task " + taskId);
		AnalysisTask task = AnalysisTaskManager.getInstance().getTask(URLDecoder.decode(taskId, "UTF-8"));

		if (task == null) {
			logger.warn("Task not found: Task " + taskId);
			return Response.status(Status.NOT_FOUND).build();
		}

		if (!task.isRunning() && !task.isDone()) {
			logger.debug("Task not yet started: Task " + taskId);
			// not yet started
			return Response
					.status(Status.ACCEPTED)
					.entity("Analysis is pending in queue and should start shortly.")
					.build();
		}

		if (task.isRunning()) {
			logger.debug("Task not yet done: Task " + taskId);
			// not yet done
			return Response.status(Status.ACCEPTED).entity("Analysis is running.").build();
		}

		if (task.isCancelled()) {
			logger.warn("Task cancelled: Task " + taskId);
			return Response.serverError().entity("Computation timeout exceeded.").build();
		}

		Object result = null;
		try {
			result = task.getResult();
			logger.debug("Task complete: Task " + taskId);
		} catch (ExecutionException e) {
			// any exceptions thrown in the analysis
			return Response.serverError().entity(e.getMessage()).build();
		}
		logger.debug("Result ok: " + result);
		return Response.ok(result).build();
	}

}
