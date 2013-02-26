/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/ExerciseGroup.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table ExerciseGroup.
 * 
 * @author S.Schwarzrock
 *
 */
public class ExerciseGroup implements IClixMappingClass {

	private long id;
	private long associatedCourse;

	public ExerciseGroup()
	{

	}

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getAssociatedCourse() {
		return this.associatedCourse;
	}

	public void setAssociatedCourse(final long associatedCourse) {
		this.associatedCourse = associatedCourse;
	}

	public String getString()
	{
		return "ExerciseGroup$$$"
				+ this.id + "$$$"
				+ this.getAssociatedCourse();
	}

}
