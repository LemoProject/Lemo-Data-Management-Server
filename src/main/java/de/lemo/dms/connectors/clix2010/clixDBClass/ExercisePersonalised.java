/**
 * File ./main/java/de/lemo/dms/connectors/clix2010/clixDBClass/ExercisePersonalised.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

/**
 * Mapping class for table ExercisePersonalised.
 * 
 * @author S.Schwarzrock
 *
 */
public class ExercisePersonalised implements IClixMappingClass {

	private ExercisePersonalisedPK id;

	private Long exercise;
	private Long user;
	private Long points;
	private String uploadDate;
	private Long exerciseSheet;
	private Long community;

	public ExercisePersonalisedPK getId() {
		return this.id;
	}

	public String getString()
	{
		return "ExercisePersonalised$$$"
				+ this.getUploadDate() + "$$$"
				+ this.getCommunity() + "$$$"
				+ this.getExercise() + "$$$"
				+ this.getExerciseSheet() + "$$$"
				+ this.getPoints() + "$$$"
				+ this.getUser();
	}

	public void setId(final ExercisePersonalisedPK id) {
		this.id = id;
	}

	public Long getExerciseSheet() {
		return this.exerciseSheet;
	}

	public void setExerciseSheet(final Long exerciseSheet) {
		this.exerciseSheet = exerciseSheet;
	}

	public Long getCommunity() {
		return this.community;
	}

	public void setCommunity(final Long community) {
		this.community = community;
	}

	public Long getExercise() {
		return this.exercise;
	}

	public void setExercise(final Long exercise) {
		this.exercise = exercise;
	}

	public Long getUser() {
		return this.user;
	}

	public void setUser(final Long user) {
		this.user = user;
	}

	public Long getPoints() {
		return this.points;
	}

	public void setPoints(final Long points) {
		this.points = points;
	}

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
