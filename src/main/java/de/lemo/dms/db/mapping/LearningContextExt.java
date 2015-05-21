package de.lemo.dms.db.mapping;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "lemo_learningContextExt")
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
	
	public void setLearningContext(final long learningContext, final Map<Long, LearningContext> learningContexts,
			final Map<Long, LearningContext> oldLearningContexts) {

		if (learningContexts.get(learningContext) != null)
		{
			this.learningContext = learningContexts.get(learningContext);
			learningContexts.get(learningContext).addLearningContextExtensions(this);
		}
		if ((this.learningContext == null) && (oldLearningContexts.get(learningContext) != null))
		{
			this.learningContext = oldLearningContexts.get(learningContext);
			oldLearningContexts.get(learningContext).addLearningContextExtensions(this);
		}
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

