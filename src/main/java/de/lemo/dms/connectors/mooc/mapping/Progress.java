package de.lemo.dms.connectors.mooc.mapping;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "progress")
public class Progress {
	
	
	private long id;
	private long membershipId;
	private long segmentId;
	private long percentage;
	private Date timeCreated;
	private Date timeModified;
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

	
	
}
