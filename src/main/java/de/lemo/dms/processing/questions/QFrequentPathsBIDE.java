/**
 * File ./src/main/java/de/lemo/dms/processing/questions/QFrequentPathsBIDE.java
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
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam(MetaParam.GENDER) List<Long> gender,
			@FormParam(MetaParam.LEARNING_OBJ_IDS) List<Long> learningObjects) throws UnsupportedEncodingException, URISyntaxException {

		validateTimestamps(startTime, endTime);

		String taskId = userId + "-" + "BIDE";
		AFrequentPathsBIDE task = new AFrequentPathsBIDE(taskId, courses, users, types, minLength, maxLength, minSup,
				sessionWise, startTime, endTime, gender, learningObjects);

		AnalysisTaskManager.getInstance().addTask(task);

		// Tell the client where to find the result
		URI resultPollingUri = new URI(ServiceTaskManager.TASK_POLLING_PATH + URLEncoder.encode(taskId, "UTF-8"));
		logger.debug("Task results created at " + resultPollingUri);

		// XXX the APPS uses the id in the entity, though it should probably use the absolute URL in the header
		return Response.created(resultPollingUri).entity(taskId).build();
	}

}
