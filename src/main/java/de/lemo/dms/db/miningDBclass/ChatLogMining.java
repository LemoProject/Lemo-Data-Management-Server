/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/ChatLogMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.db.miningDBclass;

import java.util.Map;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** 
 * This class represents the table chatlog. 
 * @author Sebastian Schwarzrock
 */
public class ChatLogMining implements IMappingClass, ILogMining {

	private long id;
	private ChatMining chat;
	private UserMining user;
	private String message;
	private long timestamp;
	private CourseMining course;
	private Long duration;
	private Long platform;
	private static final Long PREFIX = 19L;

	@Override
	public CourseMining getCourse() {
		return this.course;
	}

	public void setCourse(final CourseMining course) {
		this.course = course;
	}
	
	@Override
	public int compareTo(final ILogMining o) {
		ILogMining s;
		try {
			s = o;
		} catch (final Exception e)
		{
			return 0;
		}
		if (s != null) {
			if (this.timestamp > s.getTimestamp()) {
				return 1;
			}
			if (this.timestamp < s.getTimestamp()) {
				return -1;
			}
		}
		return 0;
	}

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (o != null) {} else return false;
		if (!(o instanceof ChatLogMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof ChatLogMining)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int)id;
	}
	
	@Override
	public void setDuration(final Long duration)
	{
		this.duration = duration;
	}

	public void setCourse(final long course, final Map<Long, CourseMining> courseMining,
			final Map<Long, CourseMining> oldCourseMining) {

		if (courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addChatLog(this);
		}
		if ((this.course == null) && (oldCourseMining.get(course) != null))
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addChatLog(this);
		}
	}

	@Override
	public long getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

	public ChatMining getChat() {
		return this.chat;
	}

	public void setChat(final ChatMining chat) {
		this.chat = chat;
	}

	@Override
	public UserMining getUser() {
		return this.user;
	}

	public void setUser(final UserMining user) {
		this.user = user;
	}

	@Override
	public long getId() {
		return this.id;
	}

	@Override
	public void setId(final long id) {
		this.id = id;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void setUser(final long user, final Map<Long, UserMining> userMining,
			final Map<Long, UserMining> oldUserMining) {

		if (userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addChatLog(this);
		}
		if ((this.user == null) && (oldUserMining.get(user) != null))
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addChatLog(this);
		}
	}

	public void setChat(final long chat, final Map<Long, ChatMining> chatMining,
			final Map<Long, ChatMining> oldChatMining)
	{

		if (chatMining.get(chat) != null)
		{
			this.chat = chatMining.get(chat);
			chatMining.get(chat).addChatLog(this);
		}
		if ((this.chat == null) && (oldChatMining.get(chat) != null))
		{
			this.chat = oldChatMining.get(chat);
			oldChatMining.get(chat).addChatLog(this);
		}
	}

	

	@Override
	public String getAction() {
		return "chat";
	}

	@Override
	public String getTitle() {
		return this.chat.getTitle();
	}

	@Override
	public Long getLearnObjId() {
		return this.getChat().getId();
	}

	@Override
	public Long getDuration() {
		return this.duration;
	}

	@Override
	public Long getPrefix() {
		return PREFIX;
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

}
