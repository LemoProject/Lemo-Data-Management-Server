package de.lemo.dms.db.mapping;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/** This class represents the table user. */
@Entity
@Table(name = "umed_person")
public class Person{
	


	private long id;
	private String name;
	
	private Set<LearningActivity> learningActivities= new HashSet<LearningActivity>();
	private Set<PersonContext> personContexts = new HashSet<PersonContext>();
	private Set<PersonExt> personExtensions = new HashSet<PersonExt>();
	
	
	/**
	 * @return the eventLogs
	 */
	@OneToMany(mappedBy="person")
	public Set<LearningActivity> getLearningActivities() {
		return learningActivities;
	}

	/**
	 * @param learningActivities the learningActivities to set
	 */
	public void setLearningActivities(Set<LearningActivity> learningActivities) {
		this.learningActivities = learningActivities;
	}
	
	public void addLearningActivity(LearningActivity learningActivity)
	{
		this.learningActivities.add(learningActivity);
	}


	public boolean equals(final Person o) {
		if ((o.getId() == this.getId()) && (o instanceof Person)) {
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
	 * @return the courseUsers
	 */
	@OneToMany(mappedBy="person")
	public Set<PersonContext> getPersonContexts() {
		return personContexts;
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

	/**
	 * @return the personExtensions
	 */
	@OneToMany(mappedBy="person")
	public Set<PersonExt> getPersonExtensions() {
		return personExtensions;
	}

	/**
	 * @param personExtensions the personExtensions to set
	 */
	public void setPersonExtensions(Set<PersonExt> personExtensions) {
		this.personExtensions = personExtensions;
	}
	
	public void addPersonExtension(PersonExt extension)
	{
		this.personExtensions.add(extension);
	}

}
