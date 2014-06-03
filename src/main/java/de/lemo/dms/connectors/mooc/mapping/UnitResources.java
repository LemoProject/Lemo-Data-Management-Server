package de.lemo.dms.connectors.mooc.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "unit_resources")
public class UnitResources {
	
	private long id;
	private long userId;
	private long unitId;
	private long timeCreated;
	private long timeModified;
	private String attachableType;
	private long position;
	
	
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
	 * @return the userId
	 */
	@Column(name="user_id")
	public long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(long userId) {
		this.userId = userId;
	}
	/**
	 * @return the unitId
	 */
	@Column(name="unit_id")
	public long getUnitId() {
		return unitId;
	}
	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(long unitId) {
		this.unitId = unitId;
	}
	/**
	 * @return the attachableType
	 */
	@Column(name="attachable_type")
	public String getAttachableType() {
		return attachableType;
	}
	/**
	 * @param attachableType the attachableType to set
	 */
	public void setAttachableType(String attachableType) {
		this.attachableType = attachableType;
	}
	/**
	 * @return the position
	 */
	@Column(name="position")
	public long getPosition() {
		return position;
	}
	/**
	 * @param position the position to set
	 */
	public void setPosition(long position) {
		this.position = position;
	}
	/**
	 * @return the timeCreated
	 */
	@Column(name="created_at")
	public long getTimeCreated() {
		return timeCreated;
	}
	/**
	 * @param timeCreated the timeCreated to set
	 */
	public void setTimeCreated(long timeCreated) {
		this.timeCreated = timeCreated;
	}
	/**
	 * @return the timeModified
	 */
	@Column(name="updated_at")
	public long getTimeModified() {
		return timeModified;
	}
	/**
	 * @param timeModified the timeModified to set
	 */
	public void setTimeModified(long timeModified) {
		this.timeModified = timeModified;
	}
}
