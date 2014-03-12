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
@Table(name = "lemo_collaborative_object_type")
public class CollaborativeObjectType implements IMapping {

	private long id;
	private String type;
	
	private Set<CollaborativeObject> collaborativeObjects = new HashSet<CollaborativeObject>();
	
	public boolean equals(final IMapping o) {
		if (!(o instanceof CollaborativeObjectType)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof CollaborativeObjectType)) {
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
	
	public void setCollaborativeObjects(final Set<CollaborativeObject> collaborativeObjects) {
		this.collaborativeObjects = collaborativeObjects;
	}

	@OneToMany(mappedBy="type")
	public Set<CollaborativeObject> getCollaborativeObjects() {
		return this.collaborativeObjects;
	}

	public void addCollaborativeObject(final CollaborativeObject collaborativeObject) {
		this.collaborativeObjects.add(collaborativeObject);
	}


}
