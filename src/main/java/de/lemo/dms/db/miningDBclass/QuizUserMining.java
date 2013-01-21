package de.lemo.dms.db.miningDBclass;

import java.util.Map;

import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/** This class represents the association between the quiz and the user.*/
public class QuizUserMining  implements IMappingClass{
	private long id;
	private UserMining user;
	private CourseMining course;
	private QuizMining quiz;	
	private double rawGrade;
	private double finalGrade;
	private long timeModified;
	private Long platform;
	
	public boolean equals(IMappingClass o)
	{
		if(!(o instanceof QuizUserMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof QuizUserMining))
			return true;
		return false;
	}
	
	/** standard getter for the attribute id
	 * @return the identifier for the association between quiz and user
	 */		
	public long getId() {
		return id;
	}
	/** standard setter for the attribute id
	 * @param id the identifier for the association between quiz and user
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard getter for the attribute user
	 * @return the user who is associated
	 */	
	public UserMining getUser() {
		return user;
	}
	/** standard setter for the attribute user
	 * @param user the user who is associated
	 */	
	public void setUser(UserMining user) {
		this.user = user;
	}
	/** parameterized setter for the attribute user
	 * @param user the id of the user who is associated
	 * @param userMining a list of new added user, which is searched for the user with the id submitted in the user parameter
	 * @param oldUserMining a list of user in the miningdatabase, which is searched for the user with the id submitted in the user parameter
	 */	
	public void setUser(long user, Map<Long, UserMining> userMining, Map<Long, UserMining> oldUserMining) {		
		
		if(userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addQuizUser(this);
		}
		if(this.user == null && oldUserMining.get(user) != null)
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addQuizUser(this);
		}
		
	}
	/** standard getter for the attribute course
	 * @return the course in which the action takes place
	 */	
	public CourseMining getCourse() {
		return course;
	}
	/** standard setter for the attribute course
	 * @param course the course in which the action takes place
	 */	
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	/** parameterized setter for the attribute course
	 * @param course the id of the course in which the action takes place
	 * @param courseMining a list of new added courses, which is searched for the course with the id submitted in the course parameter
	 * @param oldCourseMining a list of course in the miningdatabase, which is searched for the course with the id submitted in the course parameter
	 */	
	public void setCourse(long course, Map<Long, CourseMining> courseMining, Map<Long, CourseMining> oldCourseMining) {		
		
		if(courseMining.get(course) != null)
		{
			this.course = courseMining.get(course);
			courseMining.get(course).addQuizUser(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addQuizUser(this);
		}
	}
	/** standard getter for the attribute quiz
	 * @return the quiz in which the action takes place
	 */	
	public QuizMining getQuiz() {
		return quiz;
	}
	/** standard setter for the attribute quiz
	 * @param quiz the quiz in which the action takes place
	 */	
	public void setQuiz(QuizMining quiz) {
		this.quiz = quiz;
	}
	/** parameterized setter for the attribute quiz
	 * @param id the id of the quiz in which the action takes place
	 * @param quizMining a list of new added quiz, which is searched for the quiz with the qid and qtype submitted in the other parameters
	 * @param oldQuizMining a list of quiz in the miningdatabase, which is searched for the quiz with the qid and qtype submitted in the other parameters
	 */	
	public void setQuiz(long quiz, Map<Long, QuizMining> quizMining, Map<Long, QuizMining> oldQuizMining) {	
		
		if(quizMining.get(quiz) != null)
		{
			this.quiz = quizMining.get(quiz);
			quizMining.get(quiz).addQuizUser(this);
		}
		if(this.quiz == null && oldQuizMining.get(quiz) != null)
		{
			this.quiz = oldQuizMining.get(quiz);
			oldQuizMining.get(quiz).addQuizUser(this);
		}
	}
	/** standard getter for the attribute rawgrade
	 * @return the raw grade of the user in this exercise
	 */	
	public double getRawGrade() {
		return rawGrade;
	}
	/** standard setter for the attribute rawgrade
	 * @param rawGrade the raw grade of the user in this quiz
	 */	
	public void setRawGrade(double rawGrade) {
		this.rawGrade = rawGrade;
	}
	/** standard getter for the attribute finalgrade
	 * @return the final grade of the user in this quiz
	 */	
	public double getFinalGrade() {
		return finalGrade;
	}
	/** standard setter for the attribute finalgrade
	 * @param finalGrade the final grade of the user in this quiz
	 */	
	public void setFinalGrade(double finalGrade) {
		this.finalGrade = finalGrade;
	}
	/** standard getter for the attribute timemodified
	 * @return the timestamp when the grade was changed the last time
	 */	
	public long getTimeModified() {
		return timeModified;
	}
	/** standard setter for the attribute timemodified
	 * @param timeModified the timestamp when the grade was changed the last time
	 */	
	public void setTimeModified(long timeModified) {
		this.timeModified = timeModified;
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}	
}
