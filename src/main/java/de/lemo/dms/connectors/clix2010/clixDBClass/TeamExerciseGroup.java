/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/TeamExerciseGroup.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table TeamExerciseGroup.
 * 
 * @author S.Schwarzrock
 *
 */
public class TeamExerciseGroup implements IClixMappingClass {

	private Long id;
	private Long component;

	public Long getId() {
		return this.id;
	}

	public String getString()
	{
		return "TeamExerciseGroup$$$"
				+ this.id + "$$$"
				+ this.getComponent();
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public Long getComponent() {
		return this.component;
	}

	public void setComponent(final Long component) {
		this.component = component;
	}

	public TeamExerciseGroup()
	{

	}
}
