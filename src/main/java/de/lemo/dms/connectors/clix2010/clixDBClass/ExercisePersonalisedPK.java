/**
 * File ./src/main/java/de/lemo/dms/connectors/clix2010/clixDBClass/ExercisePersonalisedPK.java
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
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/ExercisePersonalisedPK.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * Class for realization of primary key for ExercisePersonalised.
 * 
 * @author S.Schwarzrock
 *
 */
@Embeddable
public class ExercisePersonalisedPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5514904557669519527L;
	private Long exercise_id;
	private Long user_id;
	private Long exerciseSheet_id;
	private Long community_id;

	public Long getExercise_id() {
		return exercise_id;
	}

	public void setExercise_id(Long exercise_id) {
		this.exercise_id = exercise_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public Long getExerciseSheet_id() {
		return exerciseSheet_id;
	}

	public void setExerciseSheet_id(Long exerciseSheet_id) {
		this.exerciseSheet_id = exerciseSheet_id;
	}

	public Long getCommunity_id() {
		return community_id;
	}

	public void setCommunity_id(Long community_id) {
		this.community_id = community_id;
	}

	@Override
	public boolean equals(final Object arg)
	{
		if (arg == null) {
			return false;
		}
		if (!(arg instanceof ExercisePersonalisedPK)) {
			return false;
		}
		final ExercisePersonalisedPK a = (ExercisePersonalisedPK) arg;
		if (a.getUser_id() != this.user_id) {
			return false;
		}
		if (a.getExercise_id() != this.exercise_id) {
			return false;
		}
		if (a.getExerciseSheet_id() != this.exerciseSheet_id) {
			return false;
		}
		if (a.getCommunity_id() != this.community_id) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		return (this.community_id.hashCode() * 17) + (this.exercise_id.hashCode() * 19)
				+ (this.exerciseSheet_id.hashCode() * 23) + (this.user_id.hashCode() * 29);
	}

	

	public ExercisePersonalisedPK()
	{

	}

}
