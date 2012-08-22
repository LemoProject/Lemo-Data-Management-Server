package de.lemo.dms.connectors.clix2010.clixDBClass;

import java.io.Serializable;

public class ExercisePersonalisedPK implements Serializable {
	
	private Long exercise;
	private Long user;
	private Long exerciseSheet;
	private Long community;
	
	public boolean equals(Object arg)
	{
		if(arg == null)
			return false;
		if(!(arg instanceof ExercisePersonalisedPK))
			return false;
		ExercisePersonalisedPK a = (ExercisePersonalisedPK)arg;
		if(a.getUser() != this.user)
			return false;
		if(a.getExercise() != this.exercise)
			return false;
		if(a.getExerciseSheet() != this.exerciseSheet)
			return false;
		if(a.getCommunity() != this.community)
			return false;
		return true;
	}
	
	public int hashCode()
	{
		return community.hashCode() * 17 + exercise.hashCode() * 19 + exerciseSheet.hashCode() * 23 + user.hashCode() * 29;
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

	public ExercisePersonalisedPK()
	{
		
	}

}
