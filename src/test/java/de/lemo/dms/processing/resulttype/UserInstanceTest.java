package de.lemo.dms.processing.resulttype;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.mapping.Course;
import de.lemo.dms.db.mapping.CourseUser;
import de.lemo.dms.db.mapping.UserAssessment;

public class UserInstanceTest {
	private UserInstance userInstance;

	@Before
	public void init(){
		ServerConfiguration.getInstance().loadConfig("/lemo");
		userInstance = generateUserInstance();
	}
	
	private UserInstance generateUserInstance(){
		UserInstance userInstance = new UserInstance();
		userInstance.setAnswerCount(4);
		userInstance.setClassId(0);
		userInstance.setCommentCount(9);
		userInstance.setDownVotes(0);
		userInstance.setWordCount(1999);
		userInstance.setLinkCount(8);
		userInstance.setProgressPercentage(30);
		userInstance.setForumUsed(true);
		return userInstance;
	}
	
	
	@Test
	public void testConstructor(){
		Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();
		Criteria criteria = session.createCriteria(CourseUser.class);
		criteria.setMaxResults(1);
		CourseUser courseUser = (CourseUser) criteria.uniqueResult();
		
		userInstance = new UserInstance(courseUser);
		UserInstance resultingUserInstance;
		resultingUserInstance = userInstance.queryUserAssessments();
		assertNotNull(resultingUserInstance.getUserId());
		session.close();
	}
}
