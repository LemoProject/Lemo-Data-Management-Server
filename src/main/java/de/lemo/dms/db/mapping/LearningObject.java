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

/** 
 * This class represents the table lemo_learning_object. 
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "lemo_learning_object")
public class LearningObject{

	private long id;
	private String name;
	private String type;
	private LearningObject parent;
	
	private Set<ObjectContext> objectContexts = new HashSet<ObjectContext>();		
	private Set<LearningActivity> learningActivities = new HashSet<LearningActivity>();
	private Set<LearningObjectExt> learningObjectExtensions = new HashSet<LearningObjectExt>();
	
	public boolean equals(final LearningObject o) {
		if ((o.getId() == this.getId()) && (o instanceof LearningObject)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}


	
	@Id
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	@Column(name="type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public void setLearningActivities(final Set<LearningActivity> learningActivities) {
		this.learningActivities = learningActivities;
	}


	@OneToMany(mappedBy="learningObject")
	public Set<LearningActivity> getLearningActivities() {
		return this.learningActivities;
	}

	public void addLearningActivity(final LearningActivity learningActivity) {
		this.learningActivities.add(learningActivity);
	}
	
	/**
	 * standard setter for the attribute course_resource.
	 * 
	 * @param courseLearningObjects
	 *            a set of entries in the course_resource table which relate the resource to the courses
	 */
	public void setObjectContexts(final Set<ObjectContext> objectContexts) {
		this.objectContexts = objectContexts;
	}

	/**
	 * standard getter for the attribute.
	 * 
	 * @return a set of entries in the course_resource table which relate the resource to the courses
	 */
	@OneToMany(mappedBy="learningObject")
	public Set<ObjectContext> getObjectContexts() {
		return this.objectContexts;
	}

	/**
	 * standard add method for the attribute course_resource.
	 * 
	 * @param courseLearningObject
	 *            this entry will be added to the list of course_resource in this resource
	 */
	public void addObjectContext(final ObjectContext objectContext) {
		this.objectContexts.add(objectContext);
	}
	
	public void setParent(final long id, final Map<Long, LearningObject> learningObjs,
			final Map<Long, LearningObject> oldLearningObjs) {

		if (learningObjs.get(id) != null)
		{
			this.parent = learningObjs.get(id);
		}
		if ((this.parent == null) && (oldLearningObjs.get(id) != null))
		{
			this.parent = oldLearningObjs.get(id);
		}
	}
	
	/**
	 * @return the parent
	 */
	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@JoinColumn(name="parent")
	public LearningObject getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(LearningObject parent) {
		this.parent = parent;
	}

	/**
	 * @return the LearningObjectExtensions
	 */
	@OneToMany(mappedBy="learningObject")
	public Set<LearningObjectExt> getLearningObjectExtensions() {
		return learningObjectExtensions;
	}

	/**
	 * @param learningObjectExtensions the LearningObjectExtensions to set
	 */
	public void setLearningObjectExtensions(Set<LearningObjectExt> learningObjectExtensions) {
		this.learningObjectExtensions = learningObjectExtensions;
	}
	
	public void addLearningObjectExt(LearningObjectExt learningObjectExtension) {
		this.learningObjectExtensions.add(learningObjectExtension);
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
