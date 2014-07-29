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
@Table(name = "lemo_attribute")
public class Attribute implements IMapping{
	
	private long id;
	private String name;
	
	private Set<CourseAttribute> courseAttributes = new HashSet<CourseAttribute>();
	private Set<LearningAttribute> learningAttributes = new HashSet<LearningAttribute>();
	private Set<UserAttribute> userAttributes = new HashSet<UserAttribute>();

	@Override
	@Id
	public long getId() {
		return id;
	}

	@Override
	public void setId(long id) {
		this.id = id;		
	}

	@Override
	public boolean equals(final IMapping o) {
		
		if (!(o instanceof Attribute)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof Attribute)) {
			return true;
		}
		return false;
	}

	/**
	 * @return the name
	 */
	@Lob
	@Column(name="name")
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the courseAttributes
	 */
	@OneToMany(mappedBy="attribute")
	public Set<CourseAttribute> getCourseAttributes() {
		return courseAttributes;
	}

	/**
	 * @param courseAttributes the courseAttributes to set
	 */
	public void setCourseAttributes(Set<CourseAttribute> courseAttributes) {
		this.courseAttributes = courseAttributes;
	}
	
	public void addCourseAttribute(CourseAttribute courseAttribute)
	{
		this.courseAttributes.add(courseAttribute);
	}
	
	public void addUserAttribute(UserAttribute userAttribute)
	{
		this.userAttributes.add(userAttribute);
	}
	
	public void addLearningAttribute(LearningAttribute learningAttribute)
	{
		this.learningAttributes.add(learningAttribute);
	}

	/**
	 * @return the userAttributes
	 */
	@OneToMany(mappedBy="attribute")
	public Set<UserAttribute> getUserAttributes() {
		return userAttributes;
	}

	/**
	 * @param userAttributes the userAttributes to set
	 */
	public void setUserAttributes(Set<UserAttribute> userAttributes) {
		this.userAttributes = userAttributes;
	}

	/**
	 * @return the learningAttributes
	 */
	@OneToMany(mappedBy="attribute")
	public Set<LearningAttribute> getLearningAttributes() {
		return learningAttributes;
	}

	/**
	 * @param learningAttributes the learningAttributes to set
	 */
	public void setLearningAttributes(Set<LearningAttribute> learningAttributes) {
		this.learningAttributes = learningAttributes;
	}
	

}
