/**
 * File ./main/java/de/lemo/dms/processing/questions/QFrequentPathsBIDE.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.processing.questions;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import de.lemo.dms.processing.AnalysisTaskManager;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.questions.async.AFrequentPathsBIDE;
import de.lemo.dms.service.ServiceTaskManager;

/**
 * Read the path data from the database and using the Bide algorithm to generate the frequent paths
 * 
 * @author Sebastian Schwarzrock
 * @author Leonard Kappe
 */
@Path("frequentPaths")
public class QFrequentPathsBIDE extends Question {

	@POST
	public Response compute(
			@FormParam(MetaParam.LEMO_USER_ID) final Long userId,
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.USER_IDS) final List<Long> users,
			@FormParam(MetaParam.TYPES) final List<String> types,
			@FormParam(MetaParam.MIN_LENGTH) final Long minLength,
			@FormParam(MetaParam.MAX_LENGTH) final Long maxLength,
			@FormParam(MetaParam.MIN_SUP) final Double minSup,
			@FormParam(MetaParam.SESSION_WISE) final boolean sessionWise,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime) throws UnsupportedEncodingException, URISyntaxException {

		validateTimestamps(startTime, endTime);

		String taskId = userId + "-" + "BIDE";
		AFrequentPathsBIDE task = new AFrequentPathsBIDE(taskId, courses, users, types, minLength, maxLength, minSup,
				sessionWise, startTime, endTime);

		AnalysisTaskManager.getInstance().addTask(task);

		// Tell the client where to find the result
		URI resultPollingUri = new URI(ServiceTaskManager.TASK_POLLING_PATH + URLEncoder.encode(taskId, "UTF-8"));
		logger.debug("Task results created at " + resultPollingUri);

		// XXX the APPS uses the id in the entity, though it should probably use the absolute URL in the header
		return Response.created(resultPollingUri).entity(taskId).build();
	}

}
