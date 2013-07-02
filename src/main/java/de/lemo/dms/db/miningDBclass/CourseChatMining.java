/**
 * File ./src/main/java/de/lemo/dms/db/miningDBclass/CourseChatMining.java
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
 * File ./main/java/de/lemo/dms/db/miningDBclass/CourseChatMining.java
 * Date 2013-03-05
 * Project Lemo Learning Analytics
 */
package de.lemo.dms.db.miningDBclass;

import java.util.Map;

import de.lemo.dms.db.miningDBclass.abstractions.ICourseLORelation;
import de.lemo.dms.db.miningDBclass.abstractions.ILearningObject;

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
	public ILearningObject getLearningObject() {
		return this.chat;
	}
	
	

}
