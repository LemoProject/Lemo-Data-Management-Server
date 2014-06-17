package de.lemo.dms.connectors.mooc.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;


@Entity
@Table(name = "answers")
public class Answers {
	
	private long id;
	private long questionId;
	private long userId;
	private long timeCreated;
	private long timeModified;
	private String content;
	/**
	 * @return the id
	 */
	@Id
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the courseId
	 */
	@Column(name="question_id")
	public long getQuestionId() {
		return questionId;
	}
	/**
	 * @param questionId the courseId to set
	 */
	public void setQuestionId(long questionId) {
		this.questionId = questionId;
	}
	/**
	 * @return the userId
	 */
	@Column(name="user_id")
	public long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}
	/**
	 * @return the timeCreated
	 */
	@Column(name="created_at")
	public long getTimeCreated() {
		return timeCreated;
	}
	/**
	 * @param timeCreated the timeCreated to set
	 */
	public void setTimeCreated(long timeCreated) {
		this.timeCreated = timeCreated;
	}
	/**
	 * @return the timeModified
	 */
	@Column(name="updated_at")
	public long getTimeModified() {
		return timeModified;
	}
	/**
	 * @param timeModified the timeModified to set
	 */
	public void setTimeModified(long timeModified) {
		this.timeModified = timeModified;
	}
	/**
	 * @return the title
	 */
	@Lob
	@Column(name="content")
	public String getContent() {
		return content;
	}
	/**
	 * @param content the title to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	

}
