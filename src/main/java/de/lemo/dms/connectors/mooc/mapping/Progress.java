package de.lemo.dms.connectors.mooc.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "progress")
public class Progress {
	
	
	private long id;
	private long membershipId;
	private long segmentId;
	private long percentage;
	private long timeCreated;
	private long timeModified;
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
	 * @return the membershipId
	 */
	@Column(name="membership_id")
	public long getMembershipId() {
		return membershipId;
	}
	/**
	 * @param membershipId the membershipId to set
	 */
	public void setMembershipId(long membershipId) {
		this.membershipId = membershipId;
	}
	/**
	 * @return the segmentId
	 */
	@Column(name="segment_id")
	public long getSegmentId() {
		return segmentId;
	}
	/**
	 * @param segmentId the segmentId to set
	 */
	public void setSegmentId(long segmentId) {
		this.segmentId = segmentId;
	}
	/**
	 * @return the percentage
	 */
	@Column(name="percentage")
	public long getPercentage() {
		return percentage;
	}
	/**
	 * @param percentage the percentage to set
	 */
	public void setPercentage(long percentage) {
		this.percentage = percentage;
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
