package de.lemo.dms.connectors.mooc.mapping;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "unit_resources")
public class UnitResources {
	
	private long id;
	private Long userId;
	private long unitId;
	private Date timeCreated;
	private Date timeModified;
	private String attachableType;
	private long position;
	private long attachableId;
	
	
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
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
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
	@Column(name = "created_at", columnDefinition="DATETIME")
	@Temporal(TemporalType.DATE)
	public Date getTimeCreated() {
		return timeCreated;
	}
	/**
	 * @param timeCreated the timeCreated to set
	 */
	public void setTimeCreated(Date timeCreated) {
		this.timeCreated = timeCreated;
	}
	/**
	 * @return the timeModified
	 */
	@Column(name = "updated_at", columnDefinition="DATETIME")
	@Temporal(TemporalType.DATE)
	public Date getTimeModified() {
		return timeModified;
	}
	/**
	 * @param timeModified the timeModified to set
	 */
	public void setTimeModified(Date timeModified) {
		this.timeModified = timeModified;
	}
	/**
	 * @return the attachableId
	 */
	@Column(name="attachable_id")
	public long getAttachableId() {
		return attachableId;
	}
	/**
	 * @param attachableId the attachableId to set
	 */
	public void setAttachableId(long attachableId) {
		this.attachableId = attachableId;
	}
}
