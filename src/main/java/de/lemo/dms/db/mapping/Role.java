package de.lemo.dms.db.mapping;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.lemo.dms.db.mapping.abstractions.IMapping;

@Entity
@Table(name = "lemo_role")
public class Role implements IMapping{

	private long id;
	private String title;
	private long sortOrder;
	private long type;
	
	private Set<CourseUser> courseUsers = new HashSet<CourseUser>();
	
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
	 * @return the title
	 */
	@Lob
	@Column(name="title")
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the sortOrder
	 */
	@Column(name="sortorder")
	public long getSortOrder() {
		return sortOrder;
	}

	/**
	 * @param sortOrder the sortOrder to set
	 */
	public void setSortOrder(long sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the type
	 */
	@Column(name="type")
	public long getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(long type) {
		this.type = type;
	}

	public boolean equals(final IMapping o) {
		if (!(o instanceof Role)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof Role)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}

	/**
	 * @return the courseUsers
	 */
	@OneToMany(mappedBy="role")
	public Set<CourseUser> getCourseUsers() {
		return courseUsers;
	}

	/**
	 * @param courseUsers the courseUsers to set
	 */
	public void setCourseUsers(Set<CourseUser> courseUsers) {
		this.courseUsers = courseUsers;
	}
	
	public void addCourseUser(CourseUser courseUser)
	{
		this.courseUsers.add(courseUser);
	}
	
}
