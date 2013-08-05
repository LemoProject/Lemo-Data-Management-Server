/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/ExerciseGroup.java
 * Lemo-Data-Management-Server for learning analytics.
 * Copyright (C) 2013
 * Leonard Kappe, Andreas Pursian, Sebastian Schwarzrock, Boris Wenzlaff
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/ExerciseGroup.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table ExerciseGroup.
 * 
 * @author S.Schwarzrock
 *
 */
@Entity
@Table(name = "EXERCISEGROUP")
public class ExerciseGroup implements IClixMappingClass {

	private long id;
	private long associatedCourse;

	@Id
	@Column(name="EXERCISEGROUP_ID")
	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@Column(name="ASSOCIATED_COURSE_ID")
	public long getAssociatedCourse() {
		return this.associatedCourse;
	}

	public void setAssociatedCourse(final long associatedCourse) {
		this.associatedCourse = associatedCourse;
	}


}
