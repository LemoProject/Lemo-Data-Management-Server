/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TeamExerciseGroupMember.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table TeamExerciseGroupMember.
 * 
 * @author S.Schwarzrock
 *
 */
public class TeamExerciseGroupMember implements IClixMappingClass {

	private Long id;
	private Long portfolio;
	private Long exerciseGroup;

	public Long getId() {
		return this.id;
	}

	public String getString()
	{
		return "TeamExerciseGroupMember$$$"
				+ this.id + "$$$"
				+ this.getExerciseGroup() + "$$$"
				+ this.getPortfolio();
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public long getPortfolio() {
		return this.portfolio;
	}

	public void setPortfolio(final Long portfolio) {
		this.portfolio = portfolio;
	}

	public long getExerciseGroup() {
		return this.exerciseGroup;
	}

	public void setExerciseGroup(final Long exerciseGroup) {
		this.exerciseGroup = exerciseGroup;
	}

	public TeamExerciseGroupMember()
	{
	}

}
