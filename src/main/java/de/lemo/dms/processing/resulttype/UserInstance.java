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
	private int upVotes;
	private int downVotes;
	private int progressPercentage;
	
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
		criteria.add(Restrictions.eq("userAssessment.course", courseUser.getCourse()));
		List<UserAssessment> userAssessments = criteria.list();
		userId = courseUser.getUser().getId();
		classId = 1;
		for(UserAssessment userAssessment : userAssessments){
			if(userAssessment.getFeedback().equals("Content_Imagecount")){
				setImageCount((int) userAssessment.getGrade());
			}
			else if(userAssessment.getFeedback().equals("Content_Linkcount")){
				setLinkCount((int) userAssessment.getGrade());
			}
			else if(userAssessment.getFeedback().equals("Content_Wordcount")){
				setWordCount((int) userAssessment.getGrade());
			}
			else if(userAssessment.getFeedback().equals("Post_Up_Votes")){
				setUpVotes((int) userAssessment.getGrade());
			}
			else if(userAssessment.getFeedback().equals("Post_Down_Votes")){
				setDownVotes((int) userAssessment.getGrade());
			}
			else if(userAssessment.getFeedback().equals("Progress_Percentage")){
				setProgressPercentage((int) userAssessment.getGrade());
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
	@XmlElement
	public int getUpVotes() {
		return upVotes;
	}

	public void setUpVotes(int upVotes) {
		this.upVotes = upVotes;
	}
	@XmlElement
	public int getDownVotes() {
		return downVotes;
	}

	public void setDownVotes(int downVotes) {
		this.downVotes = downVotes;
	}
	@XmlElement
	public int getProgressPercentage() {
		return progressPercentage;
	}

	public void setProgressPercentage(int progressPercentage) {
		this.progressPercentage = progressPercentage;
	}
}
