package de.lemo.dms.db.mapping;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.lemo.dms.db.mapping.abstractions.IMapping;

@Entity
@Table(name = "lemo_course_user")
public class CourseUser implements IMapping {
	
	private long id;
	private Course course;
	private User user;
	private Role role;
	
	@Override
	public boolean equals(final IMapping o) {
		if (!(o instanceof CourseUser)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof CourseUser)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}

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
	 * @return the course
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="course_id")
	public Course getCourse() {
		return course;
	}

	/**
	 * @param course the course to set
	 */
	public void setCourse(Course course) {
		this.course = course;
	}

	/**
	 * @return the user
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the role
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="role_id")
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	public void setCourse(final long course, final Map<Long, Course> courses,
			final Map<Long, Course> oldCourses) {
		if (courses.get(course) != null)
		{
			this.course = courses.get(course);
			courses.get(course).addCourseUser(this);
		}
		if ((this.course == null) && (oldCourses.get(course) != null))
		{
			this.course = oldCourses.get(course);
			oldCourses.get(course).addCourseUser(this);
		}
	}
	
	public void setUser(final long user, final Map<Long, User> users,
			final Map<Long, User> oldUsers) {
		if (users.get(user) != null)
		{
			this.user = users.get(user);
			users.get(user).addCourseUser(this);
		}
		if ((this.user == null) && (oldUsers.get(user) != null))
		{
			this.user = oldUsers.get(user);
			oldUsers.get(user).addCourseUser(this);
		}
	}
	
	public void setRole(final long role, final Map<Long, Role> roles,
			final Map<Long, Role> oldRoles) {
		if (roles.get(role) != null)
		{
			this.role = roles.get(role);
			roles.get(role).addCourseUser(this);
		}
		if ((this.role == null) && (oldRoles.get(role) != null))
		{
			this.role = oldRoles.get(role);
			oldRoles.get(role).addCourseUser(this);
		}
	}
	
	

}
