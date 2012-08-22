package de.lemo.dms.db.miningDBclass;

import java.util.HashMap;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** This class represents the assoziation between the quiz and the user.*/
public class QuizUserMining  implements IMappingClass{
	private long id;
	private UserMining user;
	private CourseMining course;
	private QuizMining quiz;	
	private double rawgrade;
	private double finalgrade;
	private long timemodified;
	
	public boolean equals(IMappingClass o)
	{
		if(o == null || !(o instanceof QuizUserMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof QuizUserMining))
			return true;
		return false;
	}
	
	/** standard getter for the attribut id
	 * @return the identifier for the assoziation between quiz and user
	 */		
	public long getId() {
		return id;
	}
	/** standard setter for the attribut id
	 * @param id the identifier for the assoziation between quiz and user
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard getter for the attribut user
	 * @return the user who is assoziated
	 */	
	public UserMining getUser() {
		return user;
	}
	/** standard setter for the attribut user
	 * @param user the user who is assoziated
	 */	
	public void setUser(UserMining user) {
		this.user = user;
	}
	/** parameterized setter for the attribut user
	 * @param user the id of the user who is assoziated
	 * @param userMining a list of new added user, which is searched for the user with the id submitted in the user parameter
	 * @param oldUserMining a list of user in the miningdatabase, which is searched for the user with the id submitted in the user parameter
	 */	
	public void setUser(long user, HashMap<Long, UserMining> userMining, HashMap<Long, UserMining> oldUserMining) {		
		
		if(userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addQuiz_user(this);
		}
		if(this.user == null && oldUserMining.get(user) != null)
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addQuiz_user(this);
		}
		
	}
	/** standard getter for the attribut course
	 * @return the course in wich the action takes place
	 */	
	public CourseMining getCourse() {
		return course;
	}
	/** standard setter for the attribut course
	 * @param course the course in wich the action takes place
	 */	
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	/** parameterized setter for the attribut course
	 * @param course the id of the course in wich the action takes place
	 * @param courseMining a list of new added courses, which is searched for the course with the id submitted in the course parameter
	 * @param oldCourseMining a list of course in the miningdatabase, which is searched for the course with the id submitted in the course parameter
	 */	
	public void setCourse(long course, HashMap<Long, CourseMining> courseMining, HashMap<Long, CourseMining> oldCourseMining) {		
		
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addQuiz_user(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addQuiz_user(this);
		}
	}
	/** standard getter for the attribut quiz
	 * @return the quiz in which the action takes place
	 */	
	public QuizMining getQuiz() {
		return quiz;
	}
	/** standard setter for the attribut quiz
	 * @param quiz the quiz in which the action takes place
	 */	
	public void setQuiz(QuizMining quiz) {
		this.quiz = quiz;
	}
	/** parameterized setter for the attribut quiz
	 * @param id the id of the quiz in which the action takes place
	 * @param quizMining a list of new added quiz, which is searched for the quiz with the qid and qtype submitted in the other parameters
	 * @param oldQuizMining a list of quiz in the miningdatabase, which is searched for the quiz with the qid and qtype submitted in the other parameters
	 */	
	public void setQuiz(long quiz, HashMap<Long, QuizMining> quizMining, HashMap<Long, QuizMining> oldQuizMining) {	
		
		if(quizMining.get(quiz) != null)
		{
			this.quiz = quizMining.get(quiz);
			quizMining.get(quiz).addQuiz_user(this);
		}
		if(this.quiz == null && oldQuizMining.get(quiz) != null)
		{
			this.quiz = oldQuizMining.get(quiz);
			oldQuizMining.get(quiz).addQuiz_user(this);
		}
	}
	/** standard getter for the attribut rawgrade
	 * @return the raw grade of the user in this exercise
	 */	
	public double getRawgrade() {
		return rawgrade;
	}
	/** standard setter for the attribut rawgrade
	 * @param rawgrade the raw grade of the user in this quiz
	 */	
	public void setRawgrade(double rawgrade) {
		this.rawgrade = rawgrade;
	}
	/** standard getter for the attribut finalgrade
	 * @return the final grade of the user in this quiz
	 */	
	public double getFinalgrade() {
		return finalgrade;
	}
	/** standard setter for the attribut finalgrade
	 * @param finalgrade the final grade of the user in this quiz
	 */	
	public void setFinalgrade(double finalgrade) {
		this.finalgrade = finalgrade;
	}
	/** standard getter for the attribut timemodified
	 * @return the timestamp when the grade was changed the last time
	 */	
	public long getTimemodified() {
		return timemodified;
	}
	/** standard setter for the attribut timemodified
	 * @param timemodified the timestamp when the grade was changed the last time
	 */	
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}	
}
