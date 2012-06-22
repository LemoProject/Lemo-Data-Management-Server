package de.lemo.dms.connectors.clix2010.clixDBClass;

public class ExercisePersonalised {
	
	private ExercisePersonalisedPK id;
	
	private long exercise;
	private long user;
	private long points;
	private String uploadDate;
	private long exerciseSheet;
	private long community;
	
	public ExercisePersonalisedPK getId() {
		return id;
	}

	public void setId(ExercisePersonalisedPK id) {
		this.id = id;
	}

	public long getExerciseSheet() {
		return exerciseSheet;
	}

	public void setExerciseSheet(long exerciseSheet) {
		this.exerciseSheet = exerciseSheet;
	}

	public long getCommunity() {
		return community;
	}

	public void setCommunity(long community) {
		this.community = community;
	}

	public long getExercise() {
		return exercise;
	}

	public void setExercise(long exercise) {
		this.exercise = exercise;
	}

	public long getUser() {
		return user;
	}

	public void setUser(long user) {
		this.user = user;
	}

	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
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
