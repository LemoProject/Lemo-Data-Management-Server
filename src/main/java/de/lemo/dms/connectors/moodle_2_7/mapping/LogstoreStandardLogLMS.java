package de.lemo.dms.connectors.moodle_2_7.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

	/**
	 * Mapping class for table Log.
	 * 
	 * @author S.Schwarzrock, B.Wolf
	 *
	 */

	@Entity
	@Table	(name = "mdl_logstore_standard_log")
	public class LogstoreStandardLogLMS {
		
		private long id;
		private long timecreated;
		private long user;
		private long course;
		private long contextid;
		private Long objectid;
		private String objecttable;
		private String action;
		private String target;
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
		 * @return the timecreated
		 */
		@Column(name="timecreated")
		public long getTimecreated() {
			return timecreated;
		}
		/**
		 * @param timecreated the timecreated to set
		 */
		public void setTimecreated(long timecreated) {
			this.timecreated = timecreated;
		}
		/**
		 * @return the user
		 */
		@Column(name="userid")
		public long getUser() {
			return user;
		}
		/**
		 * @param user the user to set
		 */
		public void setUser(long user) {
			this.user = user;
		}
		/**
		 * @return the course
		 */
		@Column(name="courseid")
		public long getCourse() {
			return course;
		}
		/**
		 * @param course the course to set
		 */
		public void setCourse(long course) {
			this.course = course;
		}
		/**
		 * @return the contextid
		 */
		@Column(name="contextid")
		public long getContextid() {
			return contextid;
		}
		/**
		 * @param contextid the contextid to set
		 */
		public void setContextid(long contextid) {
			this.contextid = contextid;
		}
		/**
		 * @return the objectid
		 */
		@Column(name="objectid")
		public Long getObjectid() {
			return objectid;
		}
		/**
		 * @param objectid the objectid to set
		 */
		public void setObjectid(Long objectid) {
			this.objectid = objectid;
		}
		/**
		 * @return the objecttable
		 */
		@Column(name="objecttable")
		public String getObjecttable() {
			return objecttable;
		}
		/**
		 * @param objecttable the objecttable to set
		 */
		public void setObjecttable(String objecttable) {
			this.objecttable = objecttable;
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
		 * @return the target
		 */
		@Column(name="target")
		public String getTarget() {
			return target;
		}
		/**
		 * @param target the target to set
		 */
		public void setTarget(String target) {
			this.target = target;
		}
		
		

	}

