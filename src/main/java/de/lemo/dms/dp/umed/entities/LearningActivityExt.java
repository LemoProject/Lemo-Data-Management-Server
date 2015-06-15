package de.lemo.dms.dp.umed.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "lemo_learningActivityExt")
public class LearningActivityExt{

	private long id;
	private LearningActivity learningActivity;
	private String value;
	private String attr;
	

	
	
	/**
	 * @return the value
	 */
	@Column(name="value")
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the learningActivity
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="learningActivity")
	public LearningActivity getLearningActivity() {
		return learningActivity;
	}
	/**
	 * @param learningActivity the learningActivity to set
	 */
	public void setLearningActivity(LearningActivity learningActivity) {
		this.learningActivity = learningActivity;
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


	public boolean equals(LearningActivityExt o) {
		if ((o.getId() == this.getId()) && (o instanceof LearningActivityExt)) {
			return true;
		}
		return false;
	}
	/**
	 * @return the attribute
	 */
	@Column(name="attr")
	public String getAttr() {
		return attr;
	}
	/**
	 * @param attr the attribute to set
	 */
	public void setAttr(String attr) {
		this.attr = attr;
	}
	
	
	
}

