package de.lemo.dms.db.mapping;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import de.lemo.dms.db.mapping.abstractions.IMapping;

@Entity
@Table(name = "lemo_assessment_type")
public class AssessmentType implements IMapping {
	
	private long id;
	private String type;
	
	private Set<Assessment> tasks = new HashSet<Assessment>();
	
	public boolean equals(final IMapping o) {
		if (!(o instanceof AssessmentType)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof AssessmentType)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}
	
	/**
	 * standard getter for the attribute id.
	 * 
	 * @return the identifier of the task_type
	 */
	@Id
	public long getId() {
		return id;
	}
	
	
	public void setId(long id) {
		this.id = id;
	}
	
	
	/**
	 * standard getter for the attribute type.
	 * 
	 * @return the type of the task
	 */
	@Column(name="type")
	public String getType() {
		return type;
	}
	
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setTasks(final Set<Assessment> tasks) {
		this.tasks = tasks;
	}

	@OneToMany(mappedBy="type")
	public Set<Assessment> getTasks() {
		return this.tasks;
	}

	public void addTask(final Assessment task) {
		this.tasks.add(task);
	}

}
