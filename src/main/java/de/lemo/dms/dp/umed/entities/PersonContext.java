package de.lemo.dms.dp.umed.entities;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import de.lemo.dms.dp.umed.interfaces.IContext;

@Entity
@Table(name = "lemo_person_context")
public class PersonContext{
	
	private long id;
	private IContext learningContext;
	private Person person;
	private String role;
	
	public boolean equals(final PersonContext o) {
		if ((o.getId() == this.getId()) && (o instanceof PersonContext)) {
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
	 * @return the learningContext
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="learningContext")
	public IContext getLearningContext() {
		return learningContext;
	}

	/**
	 * @param learningContext the learningContext to set
	 */
	public void setLearningContext(LearningContext learningContext) {
		this.learningContext = learningContext;
	}

	/**
	 * @return the person
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="person")
	public Person getPerson() {
		return person;
	}

	/**
	 * @param person the person to set
	 */
	public void setPerson(Person person) {
		this.person = person;
	}

	public void setLearningContext(final long learningContext, final Map<Long, LearningContext> learningContexts,
			final Map<Long, LearningContext> oldLearningContexts) {
		if (learningContexts.get(learningContext) != null)
		{
			this.learningContext = learningContexts.get(learningContext);
			learningContexts.get(learningContext).addPersonContext(this);
		}
		if ((this.learningContext == null) && (oldLearningContexts.get(learningContext) != null))
		{
			this.learningContext = oldLearningContexts.get(learningContext);
			oldLearningContexts.get(learningContext).addPersonContext(this);
		}
	}
	
	public void setPerson(final long person, final Map<Long, Person> persons,
			final Map<Long, Person> oldPersons) {
		if (persons.get(person) != null)
		{
			this.person = persons.get(person);
			persons.get(person).addPersonContext(this);
		}
		if ((this.person == null) && (oldPersons.get(person) != null))
		{
			this.person = oldPersons.get(person);
			oldPersons.get(person).addPersonContext(this);
		}
	}

	/**
	 * @return the role
	 */
	@Column(name="role")
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}
}
