package de.lemo.dms.service;

import java.util.concurrent.ExecutionException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import de.lemo.dms.processing.async.AsyncAnalysis;
import de.lemo.dms.processing.async.AsyncTaskManager;

@Path("tasks")
public class ServiceTaskManager {

	public static final String TASK_POLLING_PATH = "services/tasks/";

	@GET
	@Path("{id}")
	public Response taskResult(@PathParam("{id}") String taskID) {

		AsyncAnalysis task = AsyncTaskManager.getInstance().getTask(taskID);

		if (task == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		if (!task.isRunning() && !task.isDone()) {
			// not yet started
			return Response
					.status(Status.ACCEPTED)
					.entity("Analysis is pending in queue and should start shortly.")
					.build();
		}

		if (task.isRunning()) {
			// not yet done
			return Response.status(Status.ACCEPTED).entity("Analysis is running.").build();
		}

		if (task.isCancelled()) {
			return Response.serverError().entity("Computation timeout exceeded.").build();
		}

		Object result = null;
		try {
			result = task.getResult();
		} catch (ExecutionException e) {
			// any exceptions thrown in the analysis
			return Response.serverError().entity(e.getMessage()).build();
		}

		return Response.ok(result).build();
	}

}
