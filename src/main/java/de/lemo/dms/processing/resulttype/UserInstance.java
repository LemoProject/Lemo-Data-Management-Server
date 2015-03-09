package de.lemo.dms.processing.resulttype;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import de.lemo.dms.core.config.ServerConfiguration;
import de.lemo.dms.db.mapping.CourseUser;
import de.lemo.dms.db.mapping.Role;


@XmlRootElement
public class UserInstance {
	private long userId;
	private int imageCount;
	private int linkCount;
	private int wordCount;
	private int postCount;
	private int answerCount;
	private int commentCount;
	private int classId;
	private int upVotes;
	private int downVotes;
	private int receivedUpVotes;
	private int receivedDownVotes;
	private int progressPercentage;
	private int unitProgress;
	private int lessonProgress;
	private int postRatingSum;
	private int postRatingMax;
	private int postRatingMin;
	private boolean forumUsed;
	private long courseId;
	

	public UserInstance() {
		// TODO Auto-generated constructor stub
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
	@XmlElement
	public int getReceivedUpVotes() {
		return receivedUpVotes;
	}
	
	public void setReceivedUpVotes(int receivedUpVotes) {
		this.receivedUpVotes=receivedUpVotes;
		
	}
	@XmlElement
	public int getReceivedDownVotes() {
		return receivedDownVotes;
	}
	public void setReceivedDownVotes(int receivedDownVotes) {
		this.receivedDownVotes = receivedDownVotes;
	}
	@XmlElement
	public boolean isForumUsed() {
		return forumUsed;
	}

	public void setForumUsed(boolean forumUsed) {
		this.forumUsed = forumUsed;
	}

	public int getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(int answerCount) {
		this.answerCount = answerCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getPostCount() {
		return postCount;
	}

	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}

	public int getPostRatingSum() {
		return postRatingSum;
	}

	public void setPostRatingSum(int postRatingSum) {
		this.postRatingSum = postRatingSum;
	}

	public int getPostRatingMax() {
		return postRatingMax;
	}

	public void setPostRatingMax(int postRatingMax) {
		this.postRatingMax = postRatingMax;
	}

	public int getPostRatingMin() {
		return postRatingMin;
	}

	public void setPostRatingMin(int postRatingMin) {
		this.postRatingMin = postRatingMin;
	}

	public int getUnitProgress() {
		return unitProgress;
	}

	public void setUnitProgress(int segmentProgress) {
		this.unitProgress = segmentProgress;
	}

	public int getLessonProgress() {
		return lessonProgress;
	}

	public void setLessonProgress(int lessonProgress) {
		this.lessonProgress = lessonProgress;
	}

	public Long getRoleId() {
		Session session = ServerConfiguration.getInstance().getMiningDbHandler().getMiningSession();
		Criteria criteria = session.createCriteria(CourseUser.class);
		criteria.add(Restrictions.eq("course.id", courseId));
		criteria.add(Restrictions.eq("user.id", userId));
		criteria.setProjection(Projections.distinct(Projections.property("role")));
		Role role = (Role) criteria.uniqueResult();
		Long roleId = role==null?null:role.getId();
		session.close();
		return roleId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;		
	}
}
