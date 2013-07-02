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

/**
 * Class for realization of primary key for ExercisePersonalised.
 * 
 * @author S.Schwarzrock
 *
 */
public class ExercisePersonalisedPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5514904557669519527L;
	private Long exercise;
	private Long user;
	private Long exerciseSheet;
	private Long community;

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
		if (a.getUser() != this.user) {
			return false;
		}
		if (a.getExercise() != this.exercise) {
			return false;
		}
		if (a.getExerciseSheet() != this.exerciseSheet) {
			return false;
		}
		if (a.getCommunity() != this.community) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		return (this.community.hashCode() * 17) + (this.exercise.hashCode() * 19)
				+ (this.exerciseSheet.hashCode() * 23) + (this.user.hashCode() * 29);
	}

	public long getExerciseSheet() {
		return this.exerciseSheet;
	}

	public void setExerciseSheet(final long exerciseSheet) {
		this.exerciseSheet = exerciseSheet;
	}

	public long getCommunity() {
		return this.community;
	}

	public void setCommunity(final long community) {
		this.community = community;
	}

	public long getExercise() {
		return this.exercise;
	}

	public void setExercise(final long exercise) {
		this.exercise = exercise;
	}

	public long getUser() {
		return this.user;
	}

	public void setUser(final long user) {
		this.user = user;
	}

	public ExercisePersonalisedPK()
	{

	}

}
