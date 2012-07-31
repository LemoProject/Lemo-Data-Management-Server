package de.lemo.dms.connectors.clix2010.clixDBClass;

import de.lemo.dms.connectors.clix2010.clixDBClass.abstractions.IClixMappingClass;

public class ExercisePersonalised  implements IClixMappingClass{
	
	private ExercisePersonalisedPK id;
	
	private Long exercise;
	private Long user;
	private Long points;
	private String uploadDate;
	private Long exerciseSheet;
	private Long community;
	
	public ExercisePersonalisedPK getId() {
		return id;
	}

	public String getString()
	{
		return "ExercisePersonalisedä$"
						+this.getUploadDate()+"ä$"
						+this.getCommunity()+"ä$"
						+this.getExercise()+"ä$"
						+this.getExerciseSheet()+"ä$"
						+this.getPoints()+"ä$"
						+this.getUser();
	}
	
	public void setId(ExercisePersonalisedPK id) {
		this.id = id;
	}

	public Long getExerciseSheet() {
		return exerciseSheet;
	}

	public void setExerciseSheet(Long exerciseSheet) {
		this.exerciseSheet = exerciseSheet;
	}

	public Long getCommunity() {
		return community;
	}

	public void setCommunity(Long community) {
		this.community = community;
	}

	public Long getExercise() {
		return exercise;
	}

	public void setExercise(Long exercise) {
		this.exercise = exercise;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public Long getPoints() {
		return points;
	}

	public void setPoints(Long points) {
		this.points = points;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public ExercisePersonalised()
	{
		
	}

}
