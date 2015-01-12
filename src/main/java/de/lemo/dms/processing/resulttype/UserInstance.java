package de.lemo.dms.processing.resulttype;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.mapping.CourseUser;
import de.lemo.dms.db.mapping.UserAssessment;

@XmlRootElement
public class UserInstance {
	private long userId;
	private int imageCount;
	private int linkCount;
	private int wordCount;
	private int classId;
	
	public UserInstance(CourseUser courseUser) {
		queryUserAssessments(courseUser);
	}
	
	public UserInstance() {
		// TODO Auto-generated constructor stub
	}
	
	private void queryUserAssessments(CourseUser courseUser) {
		Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();
		Criteria criteria = session.createCriteria(UserAssessment.class,"userAssessment");
		criteria.add(Restrictions.eq("userAssessment.user", courseUser.getUser()));
		List<UserAssessment> userAssessments = criteria.list();
		userId = courseUser.getUser().getId();
		classId = 1;
		for(UserAssessment userAssessment : userAssessments){
			if(userAssessment.getFeedback().equals("Content_Imagecount")){
				imageCount = (int) userAssessment.getGrade();
			}
			else if(userAssessment.getFeedback().equals("Content_Linkcount")){
				linkCount = (int) userAssessment.getGrade();
			}
			else if(userAssessment.getFeedback().equals("Content_Wordcount")){
				wordCount = (int) userAssessment.getGrade();
			}
		}
		session.close();
	}
	@XmlElement
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	@XmlElement
	public int getImageCount() {
		return imageCount;
	}
	public void setImageCount(int imageCount) {
		this.imageCount = imageCount;
	}
	@XmlElement
	public int getLinkCount() {
		return linkCount;
	}
	public void setLinkCount(int linkCount) {
		this.linkCount = linkCount;
	}
	@XmlElement
	public int getWordCount() {
		return wordCount;
	}
	public void setWordCount(int wordCount) {
		this.wordCount = wordCount;
	}
	@XmlElement
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
}
