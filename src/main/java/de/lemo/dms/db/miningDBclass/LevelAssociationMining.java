/**
 * File ./main/java/de/lemo/dms/db/miningDBclass/LevelAssociationMining.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.db.miningDBclass;

import java.util.Map;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

public class LevelAssociationMining implements IMappingClass {

	private long id;
	private LevelMining upper;
	private LevelMining lower;
	private Long platform;

	@Override
	public boolean equals(final IMappingClass o)
	{
		if (!(o instanceof LevelAssociationMining)) {
			return false;
		}
		if ((o.getId() == this.getId()) && (o instanceof LevelAssociationMining)) {
			return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (int) id;
	}
	
	/**
	 * standard getter for the attribute id
	 * 
	 * @return the identifier for the association between levels
	 */
	@Override
	public long getId() {
		return this.id;
	}

	/**
	 * standard setter for the attribute id
	 * 
	 * @param id
	 *            the identifier for the association between levels
	 */
	public void setId(final long id) {
		this.id = id;
	}

	/**
	 * standard getter for the attribute upper
	 * 
	 * @return a level in which the resource is used
	 */
	public LevelMining getUpper() {
		return this.upper;
	}

	/**
	 * standard setter for the attribute upper
	 * 
	 * @param upper
	 *            a level in which the resource is used
	 */
	public void setUpper(final LevelMining upper) {
		this.upper = upper;
	}

	/**
	 * parameterized setter for the attribute
	 * 
	 * @param department
	 *            the id of a department in which the resource is used
	 * @param departmentMining
	 *            a list of new added departments, which is searched for the department with the id submitted in the
	 *            department parameter
	 * @param oldDepartmentMining
	 *            a list of department in the miningdatabase, which is searched for the department with the id submitted
	 *            in the department parameter
	 */
	public void setUpper(final long upper, final Map<Long, LevelMining> levelMining,
			final Map<Long, LevelMining> oldLevelMining) {
		if (levelMining.get(upper) != null)
		{
			this.upper = levelMining.get(upper);
			levelMining.get(upper).addLevelAssociation(this);
		}
		if ((this.upper == null) && (oldLevelMining.get(upper) != null))
		{
			this.upper = oldLevelMining.get(upper);
			oldLevelMining.get(upper).addLevelAssociation(this);
		}
	}

	/**
	 * standard getter for the attribute lower
	 * 
	 * @return the degree which is used in the department
	 */
	public LevelMining getLower() {
		return this.lower;
	}

	/**
	 * standard setter for the attribute lower
	 * 
	 * @param degree
	 *            the degree which is used in the department
	 */
	public void setLower(final LevelMining lower) {
		this.lower = lower;
	}

	/**
	 * parameterized setter for the attribute lower
	 * 
	 * @param lower
	 *            the id of the degree which is used in the department
	 * @param levelMining
	 *            a list of new added degree, which is searched for the degree with the id submitted in the degree
	 *            parameter
	 * @param oldLevelMining
	 *            a list of degree in the miningdatabase, which is searched for the degree with the id submitted in the
	 *            degree parameter
	 */
	public void setLower(final long lower, final Map<Long, LevelMining> levelMining,
			final Map<Long, LevelMining> oldLevelMining) {

		if (levelMining.get(lower) != null)
		{
			this.lower = levelMining.get(lower);
			levelMining.get(lower).addLevelAssociation(this);
		}
		if ((this.lower == null) && (oldLevelMining.get(lower) != null))
		{
			this.lower = oldLevelMining.get(lower);
			oldLevelMining.get(lower).addLevelAssociation(this);
		}
	}

	public Long getPlatform() {
		return this.platform;
	}

	public void setPlatform(final Long platform) {
		this.platform = platform;
	}

}
