/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/CourseChatMining.java
 * Date 2013-03-05
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.db.miningDBclass;

import java.util.Map;

import de.lemo.dms.db.miningDBclass.abstractions.ICourseLORelation;

/** 
 * This class represents the relationship between the courses and chat. 
 * @author Sebastian Schwarzrock
 */
public class CourseChatMining implements ICourseLORelation{
	
	private long id;
	private CourseMining course;
	private ChatMining chat;
	private long platform;
	
	/**
	 * parameterized setter for the attribute course
	 * 
	 * @param course
	 *            the id of a course in which the chat is used
	 * @param courseMining
	 *            a list of new added courses, which is searched for the course with the id submitted in the course
	 *            parameter
	 * @param oldCourseMining
	 *            a list of course in the miningdatabase, which is searched for the course with the id submitted in the
	 *            course parameter
	 */
	public void setCourse(final long course, final Map<Long, CourseMining> courseMining,
			final Map<Long, CourseMining> oldCourseMining) {
		if (courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addCourseChat(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addCourseChat(this);
		}
	}
	
	/**
	 * parameterized setter for the attribute course
	 * 
	 * @param chat
	 *            the id of a course in which the forum is used
	 * @param courseMining
	 *            a list of new added courses, which is searched for the course with the id submitted in the course
	 *            parameter
	 * @param oldCourseMining
	 *            a list of course in the miningdatabase, which is searched for the course with the id submitted in the
	 *            course parameter
	 */
	public void setChat(final long chat, final Map<Long, ChatMining> chatMining,
			final Map<Long, ChatMining> oldChatMining) {
		if (chatMining.get(chat) != null)
		{
			this.chat = chatMining.get(chat);
			chatMining.get(chat).addCourseChat(this);
		}
		if ((this.chat == null) && (oldChatMining.get(chat) != null))
		{
			this.chat = oldChatMining.get(chat);
			oldChatMining.get(chat).addCourseChat(this);
		}
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public CourseMining getCourse() {
		return course;
	}
	
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	
	
	public long getPlatform() {
		return platform;
	}
	
	public void setPlatform(long platform) {
		this.platform = platform;
	}

	public ChatMining getChat() {
		return chat;
	}

	public void setChat(ChatMining chat) {
		this.chat = chat;
	}

	@Override
	public Long getCourseId() {
		return this.course.getId();
	}

	@Override
	public Long getLearningObjectId() {
		return this.chat.getId();
	}
	
	

}
