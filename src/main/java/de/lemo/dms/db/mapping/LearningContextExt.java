package de.lemo.dms.db.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "umed_learningContextExt")
public class LearningContextExt{

	private long id;
	private LearningContext learningContext;
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
	 * @return the learningContext
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="learningContext")
	public LearningContext getLearningContext() {
		return learningContext;
	}
	/**
	 * @param learningContext the learningContext to set
	 */
	public void setLearningContext(LearningContext learningContext) {
		this.learningContext = learningContext;
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
	
	
	public boolean equals(LearningContextExt o) {
		if ((o.getId() == this.getId()) && (o instanceof LearningContextExt)) {
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
	 * @param attribute the attribute to set
	 */
	public void setAttr(String attr) {
		this.attr = attr;
	}
	
	
	
}

