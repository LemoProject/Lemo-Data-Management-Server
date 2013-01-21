package de.lemo.dms.db.miningDBclass;


import java.util.Map;

import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;
import de.lemo.dms.db.miningDBclass.abstractions.IMappingClass;

/**This class represents the log table for the question object.*/
public class QuestionLogMining implements ILogMining , IMappingClass{

	private long id;
	private	QuestionMining question;
	private	UserMining user;
	private CourseMining course;
	private	QuizMining quiz;
	private String type;
	private double penalty;
	private double rawGrade;
	private double finalGrade;
	private String answers;
	private long timestamp;
	private String action;
	private Long duration;
	private Long platform;
	
	@Override
	public int compareTo(ILogMining arg0) {
		ILogMining s;
		try{
			s = arg0;
		}catch(Exception e)
		{
			return 0;
		}
		if(this.timestamp > s.getTimestamp())
			return 1;
		if(this.timestamp < s.getTimestamp())
			return -1;
		return 0;
	}
	
	public boolean equals(IMappingClass o)
	{
		if(!(o instanceof QuestionLogMining))
			return false;
		if(o.getId() == this.getId() && (o instanceof QuestionLogMining))
			return true;
		return false;
	}
	
	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}
	
	public String getTitle()
	{
		return this.question == null ? null : this.question.getTitle();
	}
	
	public Long getLearnObjId()
	{
		return this.question == null ? null : this.question.getId();
	}

	/** standard getter for the attribute id
	 * @return the identifier for the log entry
	 */		
	public long getId() {
		return id;
	}
	/** standard setter for the attribute id
	 * @param id the identifier for the log entry
	 */	
	public void setId(long id) {
		this.id = id;
	}
	/** standard setter for the attribute course
	 * @param course the course in which the action takes place
	 */		
	public void setCourse(CourseMining course) {
		this.course = course;
	}
	/** standard getter for the attribute course
	 * @return the course in which the action takes place
	 */	
	public CourseMining getCourse() {
		return course;
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
			courseMining.get(course).addQuestionLog(this);
		}
		if(this.course == null && oldCourseMining.get(course) != null)
		{
			this.course = oldCourseMining.get(course);
			oldCourseMining.get(course).addQuestionLog(this);
		}
	}
	/** standard getter for the attribute type
	 * @return the type of the question
	 */	
	public String getType() {
		return type;
	}
	/** standard setter for the attribute type
	 * @param type the type of the question
	 */	
	public void setType(String type) {
		this.type = type;
	}
	/** standard getter for the attribute answers
	 * @return the answers which are submittet with this action
	 */	
	public String getAnswers() {
		return answers;
	}
	/** standard setter for the attribute answers
	 * @param answers the answers which are submittet with this action
	 */	
	public void setAnswers(String answers) {
		this.answers = answers;
	}
	/** standard getter for the attribute timestamp
	 * @return the timestamp the action did occur
	 */	
	public long getTimestamp() {
		return timestamp;
	}
	/** standard setter for the attribute timestamp
	 * @param timestamp the timestamp the action did occur
	 */	
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	/** standard getter for the attribute penalty
	 * @return the penalty on the grade in this action
	 */	
	public double getPenalty() {
		return penalty;
	}
	/** standard setter for the attribute penalty
	 * @param penalty the penalty on the grade in this action
	 */	
	public void setPenalty(double penalty) {
		this.penalty = penalty;
	}
	/** standard getter for the attribute raw_grade
	 * @return the raw grade in this action(grade without penalty)
	 */	
	public double getRawGrade() {
		return rawGrade;
	}
	/** standard setter for the attribute raw_grade
	 * @param rawGrade the raw grade in this action(grade without penalty)
	 */	
	public void setRawGrade(Double rawGrade) {
		this.rawGrade = rawGrade;
	}
	/** standard getter for the attribute finalgrade
	 * @return the final grade in this action(with penalty)
	 */	
	public Double getFinalGrade() {
		return finalGrade;
	}
	/** standard setter for the attribute finalgrade
	 * @param finalGrade the final grade in this action(with penalty)
	 */	
	public void setFinalGrade(Double finalGrade) {
		this.finalGrade = finalGrade;
	}
	/** standard getter for the attribute question
	 * @return the question with which is interacted
	 */	
	public QuestionMining getQuestion() {
		return question;
	}
	/** standard setter for the attribute question
	 * @param question the question with which is interacted
	 */	
	public void setQuestion(QuestionMining question) {
		this.question = question;
	}
	/** parameterized setter for the attribute question
	 * @param question the id of the course in which the action takes place
	 * @param questionMining a list of new added questions, which is searched for the question with the id submitted in the question parameter
	 * @param oldQuestionMining a list of questions in the miningdatabase, which is searched for the question with the id submitted in the question parameter
	 */	
	public void setQuestion(long question, Map<Long, QuestionMining> questionMining, Map<Long, QuestionMining> oldQuestionMining) {		
        
		if(questionMining.get(question) != null)
		{
			this.question = questionMining.get(question);
			questionMining.get(question).addQuestionLog(this);
		}
		if(this.question == null && oldQuestionMining.get(question) != null)
		{
			this.question = oldQuestionMining.get(question);
			oldQuestionMining.get(question).addQuestionLog(this);
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
	 * @param quiz the id of the quiz in which the action takes place
	 * @param quizMining a list of new added quiz, which is searched for the quiz with the id submitted in the quiz parameter
	 * @param oldQuizMining a list of quiz in the miningdatabase, which is searched for the quiz with the id submitted in the quiz parameter
	 */	
	public void setQuiz(long quiz, Map<Long, QuizMining> quizMining, Map<Long, QuizMining> oldQuizMining) {		
		
		if(quizMining.get(quiz) != null)
		{
			this.quiz = quizMining.get(quiz);
			quizMining.get(quiz).addQuestionLog(this);
		}
		if(this.quiz == null && oldQuizMining.get(quiz) != null)
		{
			this.quiz = oldQuizMining.get(quiz);
			oldQuizMining.get(quiz).addQuestionLog(this);
		}
	}
	/** standard getter for the attribute user
	 * @return the user who interact with the question
	 */	
	public UserMining getUser() {
		return user;
	}
	/** standard setter for the attribute user
	 * @param user the user who interact with the question
	 */	
	public void setUser(UserMining user) {
		this.user = user;
	}
	/** parameterized setter for the attribute user
	 * @param user the user who interact with the question
	 * @param userMining a list of new added user, which is searched for the user with the id submitted in the user parameter
	 * @param oldUserMining a list of user in the miningdatabase, which is searched for the user with the id submitted in the user parameter
	 */	
	public void setUser(long user, Map<Long, UserMining> userMining, Map<Long, UserMining> oldUserMining) {				
		if(userMining.get(user) != null)
		{
			this.user = userMining.get(user);
			userMining.get(user).addQuestionLog(this);
		}
		if(this.user == null && oldUserMining.get(user) != null)
		{
			this.user = oldUserMining.get(user);
			oldUserMining.get(user).addQuestionLog(this);
		}
	}
	/** standard setter for the attribute action
	 * @param action the action which occur
	 */	
	public void setAction(String action) {
		this.action = action;
	}
	/** standard getter for the attribute action
	 * @return the action which occur
	 */	
	public String getAction() {
		return action;
	}

	@Override
	public Long getPrefix() {
		return 13L;
	}

	public Long getPlatform() {
		return platform;
	}

	public void setPlatform(Long platform) {
		this.platform = platform;
	}
}
