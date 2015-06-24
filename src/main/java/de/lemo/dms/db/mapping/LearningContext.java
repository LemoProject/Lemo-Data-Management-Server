package de.lemo.dms.db.mapping;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/** This class represents the table lemo_learning_context. */
@Entity
@Table(name = "umed_learningContext")
public class LearningContext{
	
	private long id;
	private String name;
	private LearningContext parent;
	
	private Set<ObjectContext> objectContexts = new HashSet<ObjectContext>();
	private Set<LearningActivity> learningActivities = new HashSet<LearningActivity>();
	private Set<PersonContext> personContexts = new HashSet<PersonContext>();
	
	public boolean equals(final LearningContext o) {
		if ((o.getId() == this.getId()) && (o instanceof LearningContext)) {
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
	 * standard setter for the attribute course_resource
	 * 
	 * @param courseResource
	 *            a set of entries in the course_resource table which shows the resources in this course
	 */
	public void setObjectContexts(final Set<ObjectContext> objectContexts) {
		this.objectContexts = objectContexts;
	}

	/**
	 * standard getter for the attribute course_resource
	 * 
	 * @return a set of entries in the course_resource table which shows the resources in this course
	 */
	@OneToMany(mappedBy="learningContext")
	public Set<ObjectContext> getObjectContexts() {
		return this.objectContexts;
	}

	/**
	 * standard add method for the attribute course_resource
	 * 
	 * @param courseResource
	 *            this entry of the course_resource table will be added to this course
	 */
	public void addObjectContext(final ObjectContext objectContext) {
		this.objectContexts.add(objectContext);
	}
	
		
	public void setLearningActivities(final Set<LearningActivity> learningActivities) {
		this.learningActivities = learningActivities;
	}

	@OneToMany(mappedBy="learningContext")
	public Set<LearningActivity> getLearningActivities() {
		return this.learningActivities;
	}
	
	public void addLearningActivity(final LearningActivity learningActivity) {
		this.learningActivities.add(learningActivity);
	}
	
	/**
	 * @return the courseUsers
	 */
	@OneToMany(mappedBy="learningContext")
	public Set<PersonContext> getPersonContexts() {
		return this.personContexts;
	}

	/**
	 * @param courseUsers the courseUsers to set
	 */
	public void setPersonContexts(Set<PersonContext> personContexts) {
		this.personContexts = personContexts;
	}
	
	public void addPersonContext(PersonContext personContext)
	{
		this.personContexts.add(personContext);
	}

	/**
	 * @return the parent
	 */
	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@JoinColumn(name="parent")
	public LearningContext getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(LearningContext parent) {
		this.parent = parent;
	}
	
	public void setParent(Long learningContext, Map<Long, LearningContext> learningContexts, Map<Long, LearningContext> oldLearningContexts) {
		if (learningContexts.get(learningContext) != null)
		{
			this.parent = learningContexts.get(learningContext);
		}
		if ((this.parent == null) && (oldLearningContexts.get(learningContext) != null))
		{
			this.parent = oldLearningContexts.get(learningContext);
		}
	}
	
	public void addParent()
	{
		
	}

	/**
	 * @return the name
	 */
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
}
