package de.lemo.dms.db.mapping;

import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This class represents the lemo_learning_activity object table.
 * @author Sebastian Schwarzrock
 */
@Entity
@Table(name = "lemo_learning_activity")
public class LearningActivity{

	private long id;
	private LearningContext learningContext;
	private Person person;
	private LearningObject learningObject;
	private Long time;
	private String action;
	private LearningActivity reference;
	private String info;
	
	public boolean equals(final LearningActivity o) {
		if ((o.getId() == this.getId()) && (o instanceof LearningActivity)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}
	
	public int compareTo(final LearningActivity arg0) {
		LearningActivity s;
		try {
			s = arg0;
		} catch (final Exception e)
		{
			return 0;
		}
		if (s != null) {
			if (this.time > s.getTime()) {
				return 1;
			}
			if (this.time < s.getTime()) {
				return -1;
			}
		}
		return 0;
	}
	
	@Id
	public long getId() {
		return id;
	}
	
	
	
	public void setId(long id) {
		this.id = id;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="learningContext")
	public LearningContext getLearningContext() {
		return learningContext;
	}
	
	
	
	public void setLearningContext(LearningContext learningContext) {
		this.learningContext = learningContext;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="person")
	public Person getPerson() {
		return person;
	}
	
	
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="learningObject")
	public LearningObject getLearningObject() {
		return learningObject;
	}
	
	
	
	public void setLearningObject(LearningObject learningObject) {
		this.learningObject = learningObject;
	}
	
	
	@Column(name="time")
	public Long getTime() {
		return time;
	}
	
	
	
	public void setTime(Long time) {
		this.time = time;
	}
	
	
	public void setCourse(final long learningContext, final Map<Long, LearningContext> learningContexts,
			final Map<Long, LearningContext> oldLearningContexts) {

		if (learningContexts.get(learningContext) != null)
		{
			this.learningContext = learningContexts.get(learningContext);
			learningContexts.get(learningContext).addLearningActivity(this);
		}
		if ((this.learningContext == null) && (oldLearningContexts.get(learningContext) != null))
		{
			this.learningContext = oldLearningContexts.get(learningContext);
			oldLearningContexts.get(learningContext).addLearningActivity(this);
		}
	}
	
	public void setPerson(final long person, final Map<Long, Person> persons,
			final Map<Long, Person> oldPersons) {

		if (persons.get(person) != null)
		{
			this.person = persons.get(person);
			persons.get(person).addLearningActivity(this);
		}
		if ((this.person == null) && (oldPersons.get(person) != null))
		{
			this.person = oldPersons.get(person);
			oldPersons.get(person).addLearningActivity(this);
		}
	}
	
	public void setLearningObject(final long learningObject, final Map<Long, LearningObject> learningObjects,
			final Map<Long, LearningObject> oldLearningObjects) {

		if (learningObjects.get(learningObject) != null)
		{
			this.learningObject = learningObjects.get(learningObject);
			learningObjects.get(learningObject).addLearningActivity(this);
		}
		if ((this.learningObject == null) && (oldLearningObjects.get(learningObject) != null))
		{
			this.learningObject = oldLearningObjects.get(learningObject);
			oldLearningObjects.get(learningObject).addLearningActivity(this);
		}
	}

	/**
	 * @return the action
	 */
	@Column(name="action")
	public String getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * @return the reference
	 */
	@ManyToOne(fetch=FetchType.LAZY, cascade={CascadeType.ALL})
	@JoinColumn(name="reference")
	public LearningActivity getReference() {
		return reference;
	}

	/**
	 * @param reference the reference to set
	 */
	public void setReference(LearningActivity reference) {
		this.reference = reference;
	}

	/**
	 * @return the info
	 */
	@Column(name="info")
	public String getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}
}
