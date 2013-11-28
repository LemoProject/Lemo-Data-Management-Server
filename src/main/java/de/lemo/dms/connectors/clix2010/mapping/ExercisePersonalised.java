/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/mapping/ExercisePersonalised.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/mapping/ExercisePersonalised.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.mapping;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import de.lemo.dms.connectors.clix2010.mapping.abstractions.IClixMappingClass;

/**
 * Mapping class for table ExercisePersonalised.
 * 
 * @author S.Schwarzrock
 *
 */
@Entity
@Table(name = "EXERCISE_PERSONALISED")
public class ExercisePersonalised implements IClixMappingClass {

	private ExercisePersonalisedPK id;

	private Long exercise;
	private Long user;
	private Long points;
	private String uploadDate;
	private Long exerciseSheet;
	private Long community;

	@EmbeddedId
	public ExercisePersonalisedPK getId() {
		return this.id;
	}

	public void setId(final ExercisePersonalisedPK id) {
		this.id = id;
	}

	@Column(name="EXERCISESHEET_ID")
	public Long getExerciseSheet() {
		return this.exerciseSheet;
	}

	public void setExerciseSheet(final Long exerciseSheet) {
		this.exerciseSheet = exerciseSheet;
	}

	@Column(name="COMMUNITY_ID")
	public Long getCommunity() {
		return this.community;
	}

	public void setCommunity(final Long community) {
		this.community = community;
	}

	@Column(name="EXERCISE_ID")
	public Long getExercise() {
		return this.exercise;
	}

	public void setExercise(final Long exercise) {
		this.exercise = exercise;
	}

	@Column(name="USER_ID")
	public Long getUser() {
		return this.user;
	}

	public void setUser(final Long user) {
		this.user = user;
	}

	@Column(name="POINTS")
	public Long getPoints() {
		return this.points;
	}

	public void setPoints(final Long points) {
		this.points = points;
	}

	@Column(name="UPLOAD_DATE")
	public String getUploadDate() {
		return this.uploadDate;
	}

	public void setUploadDate(final String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public ExercisePersonalised()
	{

	}

}
