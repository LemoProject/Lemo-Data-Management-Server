package de.lemo.dms.processing.questions;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.mapping.CollaborationLog;
import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.CourseUser;
import de.lemo.dms.db.mapping.LearningAttribute;
import de.lemo.dms.db.mapping.UserAssessment;
import de.lemo.dms.processing.MetaParam;
import de.lemo.dms.processing.Question;
import de.lemo.dms.processing.resulttype.ResultListUserInstance;
import de.lemo.dms.processing.resulttype.UserInstance;

@Path("queryDatabase")
public class QDatabase extends Question {
	
	Session session;
	List<UserInstance> userInstances;

	@POST
	public ResultListUserInstance compute(
			@FormParam(MetaParam.COURSE_IDS) final List<Long> courses,
			@FormParam(MetaParam.START_TIME) final Long startTime,
			@FormParam(MetaParam.END_TIME) final Long endTime,
			@FormParam(MetaParam.GENDER) List<Long> gender) {

	//	validateTimestamps(startTime, endTime);
		
		List<UserInstance> studentInstances = new ArrayList<UserInstance>();
	
		session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();
		Criteria criteria = session.createCriteria(CourseUser.class);
		criteria.add(Restrictions.eq("course.id", 1L));
		List<CourseUser> courseUsers = criteria.list();
		session.close();	
		
		for(CourseUser courseUser : courseUsers){
			studentInstances.add(new UserInstance(courseUser));			
		}		
		return new ResultListUserInstance(studentInstances);
	}
}
